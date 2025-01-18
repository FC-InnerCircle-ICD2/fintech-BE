package com.inner.circle.corebackoffice.domain

import java.util.UUID
import kotlinx.datetime.LocalDateTime

data class Payment(
    val id: UUID,
    val paymentKey: String,
    val currency: Currency,
    val userId: UUID?,
    val merchantId: UUID?,
    val paymentType: PaymentType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
