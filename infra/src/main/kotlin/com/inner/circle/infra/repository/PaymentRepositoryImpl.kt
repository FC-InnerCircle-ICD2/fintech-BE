package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(paymentEntity: PaymentEntity): PaymentEntity? =
        paymentJpaRepository.saveAndFlush(paymentEntity)

    override fun findAllByAccountId(
        accountId: Long,
        page: Int,
        limit: Int
    ): List<PaymentEntity> =
        paymentJpaRepository
            .findAllByAccountId(
                accountId = accountId,
                pageable = PageRequest.of(page, limit)
            ).content

    override fun findByAccountIdAndPaymentKey(
        accountId: Long,
        paymentKey: String
    ): PaymentEntity? =
        paymentJpaRepository.findByAccountIdAndPaymentKey(
            accountId = accountId,
            paymentKey = paymentKey
        )
}
