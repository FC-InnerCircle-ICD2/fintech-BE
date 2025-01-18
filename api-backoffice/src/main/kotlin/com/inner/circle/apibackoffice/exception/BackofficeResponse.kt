package com.inner.circle.apibackoffice.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(value = Include.NON_NULL)
data class BackofficeResponse<T> private constructor(
    val ok: Boolean,
    val data: T?,
    val error: BackofficeError?
) {
    companion object {
        fun <T> ok(data: T): BackofficeResponse<T> =
            BackofficeResponse(ok = true, data = data, error = null)

        fun fail(error: BackofficeError): BackofficeResponse<Nothing> =
            BackofficeResponse(ok = false, data = null, error = error)
    }
}
