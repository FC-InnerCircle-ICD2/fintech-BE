package com.inner.circle.core.domain

data class PaymentAuth(
    val cardNumber: String,
    val isValid: Boolean
)
