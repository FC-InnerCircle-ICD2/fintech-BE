package com.inner.circle.infra.structure.repository.entity

import java.time.LocalDateTime

data class PaymentTokenEntity(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String,
    val expiresAt: LocalDateTime
)
