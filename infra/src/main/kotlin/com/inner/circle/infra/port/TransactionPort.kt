package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.TransactionEntity
import java.math.BigDecimal

fun interface TransactionPort {
    data class Request(
        val id: Long?,
        val paymentKey: String,
        val amount: BigDecimal,
        val status: String,
        val reason: String?
    )

    fun save(request: Request): TransactionEntity
}
