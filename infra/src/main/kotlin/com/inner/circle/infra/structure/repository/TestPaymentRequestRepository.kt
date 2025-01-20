package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity

interface TestPaymentRequestRepository {
    fun findByOrderId(orderId: String): PaymentRequestEntity?

    fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity?
}
