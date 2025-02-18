package com.inner.circle.api.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import com.inner.circle.core.service.dto.TransactionDto as CoreTransactionDto

data class TransactionDto(
    val id: String,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val completedAt: LocalDateTime
) {
    companion object {
        fun of(transaction: CoreTransactionDto): TransactionDto =
            TransactionDto(
                id = transaction.id.toString(),
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = TransactionStatus.of(transaction.status),
                reason = transaction.reason,
                requestedAt = transaction.requestedAt.toJavaLocalDateTime(),
                completedAt = transaction.createdAt.toJavaLocalDateTime()
            )
    }
}
