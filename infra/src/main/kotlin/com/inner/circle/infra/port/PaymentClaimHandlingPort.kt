package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto

fun interface PaymentClaimHandlingPort {
    fun handlePaymentRequestGeneration(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto
}
