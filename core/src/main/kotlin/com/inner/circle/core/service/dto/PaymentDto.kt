package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.Currency
import com.inner.circle.core.domain.PaymentType

data class PaymentDto(
    val paymentKey: String,
    val cardNumber: String,
    val currency: Currency,
    val accountId: Long?,
    val merchantId: Long,
    val paymentType: PaymentType,
    val orderId: String
)
