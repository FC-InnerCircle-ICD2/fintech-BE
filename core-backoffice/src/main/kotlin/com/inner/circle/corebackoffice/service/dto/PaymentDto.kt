package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.corebackoffice.domain.Currency
import com.inner.circle.corebackoffice.domain.Payment
import com.inner.circle.corebackoffice.domain.PaymentType
import kotlinx.datetime.LocalDateTime

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
        fun of(payment: Payment): PaymentDto =
            PaymentDto(
                id = payment.id,
                paymentKey = payment.paymentKey,
                cardNumber = payment.cardNumber,
                currency = payment.currency,
                accountId = payment.accountId,
                merchantId = payment.merchantId,
                paymentType = payment.paymentType,
                orderId = payment.orderId,
                orderName = payment.orderName,
                createdAt = payment.createdAt,
                updatedAt = payment.updatedAt
            )
    }
}
