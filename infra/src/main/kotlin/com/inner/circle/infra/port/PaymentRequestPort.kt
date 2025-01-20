package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentRequestEntity

fun interface PaymentRequestPort {
    data class Request(
        val paymentKey: String
    )

    fun findByPaymentKey(request: Request): PaymentRequestEntity
}
