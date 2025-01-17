package com.inner.circle.apibackoffice.transaction.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionDto(
    val id: UUID,
    val paymentId: UUID,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val completedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
