package com.inner.circle.core.structure.usecase

import java.math.BigDecimal

data class PaymentClaimRequest(
    val amount: BigDecimal,
    val orderId: String,
    val orderName: String,
    val successUrl: String,
    val failUrl: String
)
