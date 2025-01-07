package com.inner.circle.core.payment.claims.dto

data class PaymentResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: PaymentError?
)
