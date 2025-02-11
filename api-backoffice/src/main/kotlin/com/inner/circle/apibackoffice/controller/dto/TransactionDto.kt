package com.inner.circle.apibackoffice.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

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
        fun of(
            transaction: com.inner.circle.corebackoffice.service.dto.TransactionDto
        ): TransactionDto =
            TransactionDto(
                id = transaction.id,
                paymentKey = transaction.paymentKey,
                amount = transaction.amount,
                status = TransactionStatus.of(transaction.status),
                reason = transaction.reason,
                createdAt = transaction.createdAt.toJavaLocalDateTime(),
                updatedAt = transaction.updatedAt.toJavaLocalDateTime()
            )
    }
}
