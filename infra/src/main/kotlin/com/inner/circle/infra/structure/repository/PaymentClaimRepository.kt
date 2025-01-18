package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity

fun interface PaymentRequestRepository {
    fun save(entity: PaymentRequestEntity): PaymentRequestEntity?
    fun findByOrderId(orderId: String): PaymentRequestEntity?

    fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity?
}
