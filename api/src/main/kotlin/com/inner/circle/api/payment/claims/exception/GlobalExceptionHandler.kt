package com.inner.circle.api.payment.claims.exception

import com.inner.circle.core.payment.claims.dto.PaymentError
import com.inner.circle.core.payment.claims.dto.PaymentResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<PaymentResponse<Nothing>> {
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
        val status = HttpStatus.BAD_REQUEST
        val errorResponse =
            PaymentResponse.fail(
                PaymentError(status.toString(), "Invalid request")
            )
        return ResponseEntity(errorResponse, status)
    }
}
