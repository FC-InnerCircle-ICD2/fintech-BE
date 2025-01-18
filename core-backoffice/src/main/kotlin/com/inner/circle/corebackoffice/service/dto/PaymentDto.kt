package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.corebackoffice.domain.Currency
import com.inner.circle.corebackoffice.domain.Payment
import com.inner.circle.corebackoffice.domain.PaymentType
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
) {
    companion object {
        fun of(payment: Payment): PaymentDto =
            PaymentDto(
                payment.id,
                payment.paymentKey,
                payment.currency,
                payment.userId,
                payment.merchantId,
                payment.paymentType,
                payment.createdAt,
                payment.updatedAt
            )
    }
}
