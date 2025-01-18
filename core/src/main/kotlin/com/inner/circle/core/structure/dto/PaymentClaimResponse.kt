package com.inner.circle.core.structure.dto

import java.time.LocalDateTime

data class PaymentClaimResponse(
    val token: String,
    val expiredAt: LocalDateTime
) {
    companion object {
        fun of(
            intpuToken: String,
            tokenExpiredAt: LocalDateTime
        ): PaymentClaimResponse = PaymentClaimResponse(intpuToken, tokenExpiredAt)

        fun testOne(
            intpuToken: String,
            tokenExpiredAt: LocalDateTime
        ): PaymentClaimResponse = PaymentClaimResponse("test", LocalDateTime.now())
    }
}
