package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRequestRepositoryImpl(
    private val paymentRequestJpaRepository: PaymentRequestJpaRepository
) : PaymentRequestRepository {
    override fun findByPaymentKeyAndOrderId(paymentKey: String, orderId: String): PaymentRequestEntity =
        paymentRequestJpaRepository.findByPaymentKeyAndOrderId(paymentKey, orderId)
}
