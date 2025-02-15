package com.inner.circle.core.service.dto

data class PaymentTokenHandleDto(
    val merchantId: Long,
    val orderId: String,
    val generatedToken: String
)
