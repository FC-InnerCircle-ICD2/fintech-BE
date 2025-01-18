package com.inner.circle.corebackoffice.domain

import java.math.BigDecimal
import java.util.UUID
import kotlinx.datetime.LocalDateTime

data class PaymentTransaction(
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
