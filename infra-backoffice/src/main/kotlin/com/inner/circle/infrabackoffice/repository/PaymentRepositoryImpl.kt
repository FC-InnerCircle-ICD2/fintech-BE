package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
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
    override fun findAllByMerchantIdOrderByCreatedAtDesc(
        merchantId: Long,
        paymentKey: String?,
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
        predicates.add(criteriaBuilder.equal(root.get<Long>("merchantId"), merchantId))

        paymentKey?.let {
            predicates.add(criteriaBuilder.equal(root.get<String>("paymentKey"), it))
        }

        startDate?.let {
            val startOfDay = it.atStartOfDay()
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startOfDay))
        }

        endDate?.let {
            val endOfDay = it.atTime(LocalTime.MAX)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endOfDay))
        }

        criteriaQuery.where(*predicates.toTypedArray())
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get<LocalDate>("createdAt")))

        val query = entityManager.createQuery(criteriaQuery)
        query.firstResult = page * limit
        query.maxResults = limit

        return query.resultList
    }

    override fun findByMerchantIdAndPaymentKey(
        merchantId: Long,
        paymentKey: String
    ): PaymentEntity? =
        paymentJpaRepository.findByMerchantIdAndPaymentKey(
            merchantId = merchantId,
            paymentKey = paymentKey
        )
}
