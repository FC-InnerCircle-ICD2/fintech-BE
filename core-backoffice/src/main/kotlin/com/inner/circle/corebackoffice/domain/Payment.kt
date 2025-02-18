package com.inner.circle.corebackoffice.domain

import kotlinx.datetime.LocalDateTime

data class Payment(
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
)
