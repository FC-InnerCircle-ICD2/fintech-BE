package com.inner.circle.api.controller.request

import java.math.BigDecimal

data class PaymentClaimRequest(
    val amount: BigDecimal,
    val orderId: String,
    val orderName: String
)
