package com.inner.circle.apibackoffice.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.datetime.toJavaLocalDateTime

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
) {
    companion object {
        fun of(
            transaction: com.inner.circle.corebackoffice.service.dto.TransactionDto
        ): TransactionDto =
            TransactionDto(
                transaction.id,
                transaction.paymentId,
                transaction.amount,
                TransactionStatus.of(transaction.status),
                transaction.reason,
                transaction.requestedAt.toJavaLocalDateTime(),
                transaction.completedAt.toJavaLocalDateTime(),
                transaction.createdAt.toJavaLocalDateTime(),
                transaction.updatedAt.toJavaLocalDateTime()
            )
    }
}
