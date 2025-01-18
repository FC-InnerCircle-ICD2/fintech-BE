package com.inner.circle.apibackoffice.controller.dto

import java.time.LocalDateTime
import java.util.UUID
import kotlinx.datetime.toJavaLocalDateTime

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
        fun of(payment: com.inner.circle.corebackoffice.service.dto.PaymentDto): PaymentDto =
            PaymentDto(
                payment.id,
                payment.paymentKey,
                Currency.of(payment.currency),
                payment.userId,
                payment.merchantId,
                PaymentType.of(payment.paymentType),
                payment.createdAt.toJavaLocalDateTime(),
                payment.updatedAt.toJavaLocalDateTime()
            )
    }
}
