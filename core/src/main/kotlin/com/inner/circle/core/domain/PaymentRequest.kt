package com.inner.circle.core.domain

import java.math.BigDecimal

data class PaymentRequest(
    val orderId: String,
    val orderName: String?,
    val cardNumber: String?,
    val orderStatus: String?,
    val userId: Long?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: java.time.LocalDateTime
)
