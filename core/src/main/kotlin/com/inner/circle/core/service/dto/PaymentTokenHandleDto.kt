package com.inner.circle.core.service.dto

import java.time.LocalDateTime

data class PaymentTokenHandleDto(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String,
    val expiresAt: LocalDateTime
)
