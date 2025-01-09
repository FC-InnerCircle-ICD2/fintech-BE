package com.inner.circle.api.structure.dto

data class PaymentResponse<T> private constructor(
    val success: Boolean,
    val data: T?,
    val error: PaymentError?
) {
    companion object {
        fun <T> ok(data: T): PaymentResponse<T> =
            PaymentResponse(success = true, data = data, error = null)

        fun fail(error: PaymentError): PaymentResponse<Nothing> =
            PaymentResponse(success = false, data = null, error = error)
    }
}
