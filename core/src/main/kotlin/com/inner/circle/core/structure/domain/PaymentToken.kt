package com.inner.circle.core.structure.domain

import java.time.LocalDateTime

data class PaymentToken(val token: String, val expiresAt: LocalDateTime)
