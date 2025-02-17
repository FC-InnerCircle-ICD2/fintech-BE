package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.TransactionStatus
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
) {
    companion object {
        fun of(transaction: TransactionDto): TransactionDto =
            TransactionDto(
                id = transaction.id,
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = transaction.status,
                reason = transaction.reason,
                requestedAt = transaction.requestedAt,
                createdAt = transaction.createdAt,
                updatedAt = transaction.updatedAt
            )
    }
}
