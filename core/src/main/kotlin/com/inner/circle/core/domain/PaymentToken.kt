package com.inner.circle.core.domain

import java.time.LocalDateTime

data class PaymentToken(
    val token: String,
    val expiredAt: LocalDateTime
)
