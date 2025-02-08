package com.inner.circle.core.service.dto

data class PaymentDto(
    val paymentKey: String,
    val cardNumber: String,
    val currency: String,
    val accountId: Long?,
    val merchantId: String,
    val paymentType: String,
    val orderId: String
)
