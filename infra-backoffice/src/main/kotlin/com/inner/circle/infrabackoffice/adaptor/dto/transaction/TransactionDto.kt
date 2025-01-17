package com.inner.circle.infrabackoffice.adaptor.dto.transaction

import com.inner.circle.infrabackoffice.repository.entity.TransactionStatus
import java.math.BigDecimal
import java.util.UUID
import kotlinx.datetime.LocalDateTime

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
