package com.inner.circle.core.domain

data class Payment(
    val paymentKey: String,
    val currency: String,
    val accountId: Long,
    val merchantId: String,
    val paymentType: String,
    val orderId: String
)
