package com.inner.circle.api.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(value = Include.NON_NULL)
data class PaymentResponse<T> private constructor(
    val ok: Boolean,
    val data: T?,
    val error: PaymentError?
) {
    companion object {
        fun <T> ok(data: T): PaymentResponse<T> =
            PaymentResponse(ok = true, data = data, error = null)

        fun fail(error: PaymentError): PaymentResponse<Any> =
            PaymentResponse(ok = false, data = null, error = error)
    }
}
