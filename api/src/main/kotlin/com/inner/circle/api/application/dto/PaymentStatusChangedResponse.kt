package com.inner.circle.api.application.dto

data class PaymentStatusChangedResponse(
    val status: String,
    val orderId: String,
    val merchantId: String
) {
    companion object {
        fun of(
            status: String,
            orderId: String,
            merchantId: String
        ) = PaymentStatusChangedResponse(
            status = status,
            orderId = orderId,
            merchantId = merchantId
        )
    }
}
