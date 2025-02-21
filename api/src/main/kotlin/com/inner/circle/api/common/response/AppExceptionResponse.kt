package com.inner.circle.api.common.response

import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus


data class AppExceptionResponse(
    val code: HttpStatus,
    val message: String
) {
    companion object {
        fun of(exception: AppException): AppExceptionResponse =
            AppExceptionResponse(
                code = exception.status,
                message = exception.message
            )
    }
}
