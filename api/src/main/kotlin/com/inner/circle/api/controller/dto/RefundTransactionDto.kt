package com.inner.circle.api.controller.dto

import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime
import com.inner.circle.core.service.dto.TransactionDto as CoreTransactionDto

data class RefundTransactionDto(
    val id: Long,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(transaction: CoreTransactionDto): RefundTransactionDto =
            RefundTransactionDto(
                id = transaction.id,
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = TransactionStatus.of(transaction.status),
                reason = transaction.reason,
                requestedAt = transaction.requestedAt,
                createdAt = transaction.createdAt,
                updatedAt = transaction.updatedAt
            )
    }
}
