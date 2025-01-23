package com.inner.circle.infra.adaptor.dto

import kotlinx.datetime.LocalDateTime

data class PaymentRequestDto(
    val userName: String,
    val amount: Long,
    val requestAt: LocalDateTime
)
