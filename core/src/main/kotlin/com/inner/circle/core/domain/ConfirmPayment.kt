package com.inner.circle.core.domain

import java.math.BigDecimal

data class ConfirmPayment(
    val paymentKey: String,
    val merchantId: Long,
    val orderId: String,
    val cardNumber: String,
    val amount: BigDecimal
)
