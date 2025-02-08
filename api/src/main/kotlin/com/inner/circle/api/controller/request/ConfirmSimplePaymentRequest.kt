package com.inner.circle.api.controller.request

data class ConfirmSimplePaymentRequest(
        val orderId: String,
        val merchantId: String
    )
