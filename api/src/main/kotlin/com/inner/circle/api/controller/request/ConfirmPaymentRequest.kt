package com.inner.circle.api.controller.request

data class ConfirmPaymentRequest(
    val merchantId: String,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String
)
