package com.inner.circle.core.domain

import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime

// TODO: 조회 시에는 사용하진 않는데 저장 시점에 사용하도록 수정 필요.
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
