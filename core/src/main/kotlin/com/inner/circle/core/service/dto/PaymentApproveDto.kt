package com.inner.circle.core.service.dto

import java.math.BigDecimal

data class PaymentApproveDto(
    val paymentKey: String,
    val orderId: String,
    val amount: BigDecimal
)
