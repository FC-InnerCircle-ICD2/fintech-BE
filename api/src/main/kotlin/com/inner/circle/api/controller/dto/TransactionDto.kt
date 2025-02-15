package com.inner.circle.api.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import com.inner.circle.core.service.dto.TransactionDto as CoreTransactionStatus

data class TransactionDto(
    val id: Long,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val completedAt: LocalDateTime
) {
    companion object {
        fun of(transaction: CoreTransactionStatus): TransactionDto =
            TransactionDto(
                id = transaction.id,
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = TransactionStatus.of(transaction.status),
                reason = transaction.reason,
                requestedAt = transaction.createdAt.toJavaLocalDateTime(),
                completedAt = transaction.requestedAt.toJavaLocalDateTime()
            )
    }
}
