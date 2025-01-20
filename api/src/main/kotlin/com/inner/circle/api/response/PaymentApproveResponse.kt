package com.inner.circle.api.response

import java.math.BigDecimal

data class PaymentApproveResponse  (
    val order_id: String,
    val paymentKey: String,
    val amount: BigDecimal
)
