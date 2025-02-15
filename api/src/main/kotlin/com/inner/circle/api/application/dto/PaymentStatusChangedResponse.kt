package com.inner.circle.api.application.dto

data class PaymentStatusChangedResponse(
    val status: String,
    val orderId: String,
    val merchantId: Long
) {
    companion object {
        fun of(
            status: String,
            orderId: String,
            merchantId: Long
        ) = PaymentStatusChangedResponse(
            status = status,
            orderId = orderId,
            merchantId = merchantId
        )
    }
}
