package com.inner.circle.core.usecase

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

fun interface PaymentClaimUseCase {
    data class PaymentClaimRequest
        @JsonCreator
        constructor(
            @JsonProperty("amount") val amount: BigDecimal,
            @JsonProperty("orderId") val orderId: String,
            @JsonProperty("orderName") val orderName: String
        )

    data class PaymentClaimResponse
        @JsonCreator
        constructor(
            @JsonProperty("token") val token: String,
            @JsonProperty("expiredAt") val expiredAt: LocalDateTime
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
