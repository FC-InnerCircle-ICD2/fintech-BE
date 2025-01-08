package com.inner.circle.core.payment.claims.dto

data class PaymentResponse<T> private constructor(
    val success: Boolean,
    val data: T?,
    val error: PaymentError?
) {
    companion object {
        fun <T> ok(data: T): PaymentResponse<T> {
            return PaymentResponse(success = true, data = data, error = null)
        }

        fun fail(error: PaymentError): PaymentResponse<Nothing> {
            return PaymentResponse(success = false, data = null, error = error)
        }
    }
}
