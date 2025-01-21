package com.inner.circle.core.structure.dto

import java.time.LocalDateTime

data class PaymentClaimResponse(
    val token: String,
    val expiredAt: LocalDateTime
) {
    companion object {
        fun of(
            inputToken: String,
            tokenExpiredAt: LocalDateTime
        ): PaymentClaimResponse = PaymentClaimResponse(inputToken, tokenExpiredAt)

        fun testOne(): PaymentClaimResponse = PaymentClaimResponse("test", LocalDateTime.now())
    }
}
