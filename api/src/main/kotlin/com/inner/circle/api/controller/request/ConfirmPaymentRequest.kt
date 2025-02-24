package com.inner.circle.api.controller.request

data class ConfirmPaymentRequest(
    val orderId: String,
    val merchantId: Long,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String,
    val cardCompany: String
)
