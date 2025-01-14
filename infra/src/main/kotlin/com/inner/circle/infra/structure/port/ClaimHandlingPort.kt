package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.repository.entity.PaymentRequest

fun interface ClaimHandlingPort {
    fun generatePaymentRequest(paymentRequest: PaymentRequest): PaymentRequest?
}
