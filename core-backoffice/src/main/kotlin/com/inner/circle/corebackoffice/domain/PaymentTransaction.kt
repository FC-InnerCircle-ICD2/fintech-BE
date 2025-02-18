package com.inner.circle.corebackoffice.domain

import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime

data class PaymentTransaction(
    val id: Long,
    val paymentKey: String,
    val amount: BigDecimal,
    val status: TransactionStatus,
    val reason: String?,
    val requestedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
