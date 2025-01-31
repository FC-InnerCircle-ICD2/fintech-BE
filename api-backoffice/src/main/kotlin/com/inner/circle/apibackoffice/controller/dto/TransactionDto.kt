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
                id = transaction.id,
                paymentId = transaction.paymentId,
                amount = transaction.amount,
                status = TransactionStatus.of(transaction.status),
                reason = transaction.reason,
                requestedAt = transaction.requestedAt.toJavaLocalDateTime(),
                completedAt = transaction.completedAt.toJavaLocalDateTime(),
                createdAt = transaction.createdAt.toJavaLocalDateTime(),
                updatedAt = transaction.updatedAt.toJavaLocalDateTime()
            )
    }
}
