package com.inner.circle.infra.adaptor.dto

import kotlinx.datetime.LocalDateTime

data class PaymentDto(
    val userName: String,
    val amount: Long,
    val requestAt: LocalDateTime
)
