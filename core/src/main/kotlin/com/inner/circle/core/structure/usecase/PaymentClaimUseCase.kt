package com.inner.circle.core.structure.usecase

import com.inner.circle.core.structure.dto.PaymentClaimResponse
import java.math.BigDecimal

fun interface PaymentClaimUseCase {
    data class PaymentClaimRequest(
        val amount: BigDecimal,
        val orderId: String,
        val orderName: String,
        val successUrl: String,
        val failUrl: String
    )

    fun createPayment(
        request: PaymentClaimRequest,
        merchantId: String
    ): PaymentClaimResponse
}
