package com.inner.circle.infra.structure.adaptor.dto

import java.time.LocalDateTime

data class PaymentTokenDto(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String,
    val expiresAt: LocalDateTime
)
