package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.structure.adaptor.dto.PaymentTokenDto

fun interface ClaimHandlingPort {
    fun generatePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto
}
