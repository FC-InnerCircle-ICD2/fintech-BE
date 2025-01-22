package com.inner.circle.core.domain

import java.math.BigDecimal

data class MobilePayment(
    val paymentKey: String,
    val orderId: String,
    val cardNumber: String,
    val amount: BigDecimal
)
