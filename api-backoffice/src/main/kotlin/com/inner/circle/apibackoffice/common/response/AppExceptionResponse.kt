package com.inner.circle.apibackoffice.common.response

import com.inner.circle.exception.AppException
import org.springframework.http.HttpStatus

data class AppExceptionResponse(
    val code: HttpStatus,
    val message: String
) {
    companion object {
        fun of(exception: AppException): AppExceptionResponse =
            AppExceptionResponse(
                code = HttpStatus.valueOf(exception.status.code),
                message = exception.message
            )
    }
}
