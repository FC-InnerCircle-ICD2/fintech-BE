package com.inner.circle.core.usecase

import java.math.BigDecimal
import java.time.LocalDateTime

fun interface PaymentClaimUseCase {
    data class PaymentClaimRequest(
        val amount: BigDecimal,
        val orderId: String,
        val orderName: String,
        val successUrl: String,
        val failUrl: String
    )

    data class PaymentClaimResponse(
        val token: String,
        val expiredAt: LocalDateTime
    ) {
        companion object {
            fun of(
                inputToken: String,
                tokenExpiredAt: LocalDateTime
            ): PaymentClaimResponse = PaymentClaimResponse(inputToken, tokenExpiredAt)
        }
    }

    fun createPayment(
        request: PaymentClaimRequest,
        merchantId: String
    ): PaymentClaimResponse
}
