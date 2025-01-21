package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto

fun interface ClaimHandlingPort {
    fun generatePaymentRequest(paymentRequest: PaymentClaimDto): PaymentClaimDto
}
