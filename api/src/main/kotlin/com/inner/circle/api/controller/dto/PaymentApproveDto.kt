package com.inner.circle.api.controller.dto

import java.math.BigDecimal

data class PaymentApproveDto(
    val orderId: String,
    val paymentKey: String,
    val amount: BigDecimal
)
