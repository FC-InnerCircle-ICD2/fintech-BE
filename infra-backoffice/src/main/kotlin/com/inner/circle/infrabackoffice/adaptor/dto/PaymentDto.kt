package com.inner.circle.infrabackoffice.adaptor.dto

import com.inner.circle.infrabackoffice.repository.entity.Currency
import com.inner.circle.infrabackoffice.repository.entity.PaymentType
import java.util.UUID
import kotlinx.datetime.LocalDateTime

data class PaymentDto(
    val id: UUID,
    val paymentKey: String,
    val currency: Currency,
    val userId: UUID?,
    val merchantId: UUID?,
    val paymentType: PaymentType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
