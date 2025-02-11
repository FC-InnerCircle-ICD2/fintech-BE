package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime

fun interface TransactionPort {
    data class Request(
        val id: Long?,
        val paymentKey: String,
        val amount: BigDecimal,
        val status: TransactionStatus,
        val reason: String?,
        val requestedAt: LocalDateTime
    )

    fun save(request: Request)
}
