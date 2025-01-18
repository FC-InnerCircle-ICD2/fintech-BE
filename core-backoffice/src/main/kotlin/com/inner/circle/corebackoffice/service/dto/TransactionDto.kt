package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.corebackoffice.domain.PaymentTransaction
import com.inner.circle.corebackoffice.domain.TransactionStatus
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
) {
    companion object {
        fun of(transaction: PaymentTransaction): TransactionDto =
            TransactionDto(
                id = transaction.id,
                paymentId = transaction.paymentId,
                amount = transaction.amount,
                status = transaction.status,
                reason = transaction.reason,
                requestedAt = transaction.requestedAt,
                completedAt = transaction.completedAt,
                createdAt = transaction.createdAt,
                updatedAt = transaction.updatedAt
            )
    }
}
