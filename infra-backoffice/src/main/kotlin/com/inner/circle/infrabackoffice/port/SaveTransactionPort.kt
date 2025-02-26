package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto
import com.inner.circle.infrabackoffice.repository.entity.TransactionStatus
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime

fun interface SaveTransactionPort {
    data class Request(
        val id: Long?,
        val paymentKey: String,
        val amount: BigDecimal,
        val status: TransactionStatus,
        val reason: String?,
        val requestedAt: LocalDateTime
    )

    fun save(request: Request): TransactionDto
}
