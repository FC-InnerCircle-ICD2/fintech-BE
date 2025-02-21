package com.inner.circle.api.common.response

import com.inner.circle.exception.AppException

data class AppExceptionResponse(
    val code: String,
    val message: String
) {
    companion object {
        fun of(exception: AppException): AppExceptionResponse =
            AppExceptionResponse(
                code = exception.status.name,
                message = exception.message
            )
    }
}
