package com.inner.circle.core.service.dto

data class PaymentDto(
    val paymentKey: String,
    val currency: String,
    val userId: String,
    val merchantId: String,
    val paymentType: String,
    val orderId: String
)
