package com.inner.circle.core.payment.claims.dto.request

import java.math.BigDecimal

data class PaymentClaimRequest(
    val amount: BigDecimal,
    val store: String,
    val orderId: String,
    val orderName: String,
    val successUrl: String,
    val failUrl: String
)
