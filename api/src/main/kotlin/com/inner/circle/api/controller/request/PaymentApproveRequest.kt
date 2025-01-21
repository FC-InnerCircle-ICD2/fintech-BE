package com.inner.circle.api.controller.request

import kotlinx.serialization.Serializable

@Serializable
data class PaymentApproveRequest  (
    val order_id: String,
    val paymentKey: String,
    val amount: Int
)

