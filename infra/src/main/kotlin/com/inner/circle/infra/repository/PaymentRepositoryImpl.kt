package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import java.time.LocalDate
import java.time.LocalTime
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val entityManager: EntityManager,
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(paymentEntity: PaymentEntity): PaymentEntity? =
        paymentJpaRepository.saveAndFlush(paymentEntity)

    override fun findAllByAccountId(
        accountId: Long,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        limit: Int
    ): List<PaymentEntity> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<PaymentEntity> =
            criteriaBuilder.createQuery(
                PaymentEntity::class.java
            )
        val root: Root<PaymentEntity> = criteriaQuery.from(PaymentEntity::class.java)

        val predicates: MutableList<Predicate> = mutableListOf()

        startDate?.let {
            val startOfDay = it.atStartOfDay()
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startOfDay))
        }

        endDate?.let {
            val endOfDay = it.atTime(LocalTime.MAX)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endOfDay))
        }

        criteriaQuery.where(*predicates.toTypedArray())

        val query = entityManager.createQuery(criteriaQuery)
        query.firstResult = page * limit
        query.maxResults = limit

        return query.resultList
    }

    override fun findByAccountIdAndPaymentKey(
        accountId: Long,
        paymentKey: String
    ): PaymentEntity? =
        paymentJpaRepository.findByAccountIdAndPaymentKey(
            accountId = accountId,
            paymentKey = paymentKey
        )
}
