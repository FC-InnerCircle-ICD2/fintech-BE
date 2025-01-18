package com.inner.circle.core.structure.domain

data class PaymentAuth(
    val cardNumber: String,
    val isValid: Boolean
)
