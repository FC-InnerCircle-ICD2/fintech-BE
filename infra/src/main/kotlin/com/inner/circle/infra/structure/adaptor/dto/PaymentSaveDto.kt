package com.inner.circle.infra.structure.adaptor.dto

import kotlinx.datetime.LocalDateTime

data class PaymentSaveDto(
    val userName: String,
    val amount: Long,
    val requestAt: LocalDateTime
)
