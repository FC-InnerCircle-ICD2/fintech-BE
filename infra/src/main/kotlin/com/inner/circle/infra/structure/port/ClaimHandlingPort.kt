package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.adaptor.dto.PaymentRequestDto

fun interface ClaimHandlingPort {
    fun generatePaymentRequest(paymentRequest: PaymentRequestDto): PaymentRequestDto
}
