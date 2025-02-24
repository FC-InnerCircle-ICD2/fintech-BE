package com.inner.circle.infrabackoffice.adaptor.dto

import com.inner.circle.infrabackoffice.repository.entity.Currency
import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
import com.inner.circle.infrabackoffice.repository.entity.PaymentType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

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
        fun of(payment: PaymentEntity): PaymentDto =
            PaymentDto(
                id = requireNotNull(payment.id),
                paymentKey = payment.paymentKey,
                cardNumber = payment.cardNumber,
                currency = payment.currency,
                accountId = requireNotNull(payment.accountId),
                merchantId = payment.merchantId,
                paymentType = payment.paymentType,
                orderId = payment.orderId,
                orderName = payment.orderName,
                createdAt = payment.createdAt.toKotlinLocalDateTime(),
                updatedAt = payment.updatedAt.toKotlinLocalDateTime()
            )
    }
}
