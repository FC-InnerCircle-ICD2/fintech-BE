package com.inner.circle.apibackoffice.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.inner.circle.apibackoffice.exception.BackofficeError

@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class BackofficeResponse<T> private constructor(
    val ok: Boolean,
    val data: T?,
    val error: BackofficeError?
) {
    companion object {
        fun <T> ok(data: T): BackofficeResponse<T> =
            BackofficeResponse(ok = true, data = data, error = null)

        fun fail(error: BackofficeError): BackofficeResponse<Any> =
            BackofficeResponse(ok = false, data = null, error = error)
    }
}
