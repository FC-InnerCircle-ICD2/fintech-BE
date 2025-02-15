package com.inner.circle.infra.adaptor.dto

import com.inner.circle.infra.repository.entity.TransactionStatus
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime

data class TransactionDto(
    val id: Long,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
