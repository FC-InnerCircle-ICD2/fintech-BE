package com.inner.circle.api.controller.request

import java.math.BigDecimal

data class PaymentApproveRequest(
        val orderId: String,
        val paymentKey: String,
        val amount: BigDecimal
    )
