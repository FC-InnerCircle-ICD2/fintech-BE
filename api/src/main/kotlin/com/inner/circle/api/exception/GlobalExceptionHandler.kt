package com.inner.circle.api.exception

import com.inner.circle.api.common.response.AppExceptionResponse
import com.inner.circle.api.structure.dto.PaymentError
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.exception.AppException
import javax.security.sasl.AuthenticationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(AppException::class)
    fun handleAppException(exception: AppException): ResponseEntity<AppExceptionResponse> {
        logger.error("AppException (type = {})", exception::class.simpleName, exception)
        return ResponseEntity(
            AppExceptionResponse.of(exception),
            HttpStatus.valueOf(exception.status.code)
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthorizationException(
        exception: AuthenticationException
    ): ResponseEntity<AppExceptionResponse> = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<PaymentResponse<Nothing>> {
        logger.error("An unexpected error occurred", ex)
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse =
            PaymentResponse.fail(
                PaymentError(status.toString(), "Unexpected error occurred.")
            )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<PaymentResponse<Nothing>> {
        logger.error("invalid request occurred", ex)
        val status = HttpStatus.BAD_REQUEST
        val errorResponse =
            PaymentResponse.fail(
                PaymentError(status.toString(), "Invalid request")
            )
        return ResponseEntity(errorResponse, status)
    }
}
