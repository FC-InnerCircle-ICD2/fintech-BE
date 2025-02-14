package com.inner.circle.core.service.dto

data class PaymentTokenHandleDto(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String
)
