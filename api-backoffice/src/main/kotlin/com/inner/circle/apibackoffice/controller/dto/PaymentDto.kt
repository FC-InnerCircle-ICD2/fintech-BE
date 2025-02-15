package com.inner.circle.apibackoffice.controller.dto

import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

data class PaymentDto(
    val id: Long,
    val paymentKey: String,
    val cardNumber: String,
    val currency: Currency,
    val accountId: Long?,
    val merchantId: Long,
    val paymentType: PaymentType,
    val orderId: String,
    val orderName: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(payment: com.inner.circle.corebackoffice.service.dto.PaymentDto): PaymentDto =
            PaymentDto(
                id = payment.id,
                paymentKey = payment.paymentKey,
                cardNumber = payment.cardNumber,
                currency = Currency.of(payment.currency),
                accountId = payment.accountId,
                merchantId = payment.merchantId,
                paymentType = PaymentType.of(payment.paymentType),
                orderId = payment.orderId,
                orderName = payment.orderName,
                createdAt = payment.createdAt.toJavaLocalDateTime(),
                updatedAt = payment.updatedAt.toJavaLocalDateTime()
            )
    }
}
