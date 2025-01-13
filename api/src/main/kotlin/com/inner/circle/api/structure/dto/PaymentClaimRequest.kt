package com.inner.circle.api.structure.dto

import java.math.BigDecimal

data class PaymentClaimRequest(
    val amount: BigDecimal,
    val store: String,
    val orderId: String,
    val orderName: String,
    val successUrl: String,
    val failUrl: String
)
