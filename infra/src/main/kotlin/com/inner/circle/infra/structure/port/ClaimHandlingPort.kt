package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity

fun interface ClaimHandlingPort {
    fun generatePaymentRequest(paymentRequest: PaymentRequestEntity): PaymentRequestEntity
}
