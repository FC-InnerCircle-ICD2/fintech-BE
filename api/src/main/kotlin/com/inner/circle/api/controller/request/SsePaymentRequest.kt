package com.inner.circle.api.controller.request

data class SsePaymentRequest(
    val orderId: String,
    val merchantId: String
)
