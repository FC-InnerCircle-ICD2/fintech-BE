package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentRequestEntity

fun interface PaymentRequestPort {
    data class Request(
        val paymentKey: String,
        val orderId: String
    )

    fun findByPaymentKeyAndOrderId(request: Request): PaymentRequestEntity
}
