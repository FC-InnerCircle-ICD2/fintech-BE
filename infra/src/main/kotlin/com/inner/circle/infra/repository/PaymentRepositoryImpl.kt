package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(paymentEntity: PaymentEntity): PaymentEntity? =
        paymentJpaRepository.saveAndFlush(paymentEntity)

    override fun findByAccountIdAndPaymentKey(
        accountId: Long,
        paymentKey: String
    ): PaymentEntity? =
        paymentJpaRepository.findByAccountIdAndPaymentKey(
            accountId = accountId,
            paymentKey = paymentKey
        )
}
