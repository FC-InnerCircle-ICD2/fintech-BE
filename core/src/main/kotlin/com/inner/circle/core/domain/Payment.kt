package com.inner.circle.core.domain

data class Payment(
    val paymentKey: String,
    val currency: String,
    val userId: Long,
    val merchantId: String,
    val paymentType: String,
    val orderId: String
)
