package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.corebackoffice.domain.PaymentTransaction
import com.inner.circle.corebackoffice.domain.TransactionStatus
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime

data class TransactionDto(
    val id: Long,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(transaction: PaymentTransaction): TransactionDto =
            TransactionDto(
                id = transaction.id,
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = transaction.status,
                reason = transaction.reason,
                createdAt = transaction.createdAt,
                updatedAt = transaction.updatedAt
            )
    }
}
