package com.inner.circle.core.service.dto

import java.math.BigDecimal

data class PaymentRequestDto(
    val orderId: String,
    val orderName: String,
    val cardNumber: String,
    val orderStatus: String,
    val userId: String,
    val merchantId: String,
    val paymentKey: String,
    val amount: BigDecimal,
    val paymentType: String,

    val requestTime: java.time.LocalDateTime
)
