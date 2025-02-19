package com.inner.circle.api.controller.request

import java.math.BigDecimal

data class RefundPaymentRequest(
    val paymentKey: String,
    val amount: BigDecimal
)
