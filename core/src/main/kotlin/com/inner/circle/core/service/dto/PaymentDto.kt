package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentType

data class PaymentDto(
    val paymentKey: String,
    val cardNumber: String,
    val currency: String,
    val accountId: Long?,
    val merchantId: String,
    val paymentType: PaymentType,
    val orderId: String
)
