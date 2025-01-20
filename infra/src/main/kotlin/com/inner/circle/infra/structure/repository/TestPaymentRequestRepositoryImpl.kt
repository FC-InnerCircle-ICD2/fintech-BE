package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

internal class TestPaymentRequestRepositoryImpl(
    private val paymentRequestJpaRepository: TestPaymentRequestJpaRepository
) : TestPaymentRequestRepository {
    override fun findByOrderId(orderId: String): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByOrderId(orderId)

    override fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity? =
        paymentRequestJpaRepository.save(paymentRequestEntity)
}
