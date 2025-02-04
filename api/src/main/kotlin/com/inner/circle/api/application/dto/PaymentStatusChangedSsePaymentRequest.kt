package com.inner.circle.api.application.dto

data class PaymentStatusChangedSsePaymentRequest(
    val eventType: String,
    val status: String,
    val orderId: String,
    val merchantId: String
)
