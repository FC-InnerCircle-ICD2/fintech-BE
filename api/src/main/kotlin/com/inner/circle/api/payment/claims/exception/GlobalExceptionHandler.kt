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
        val errorResponse = PaymentResponse<Nothing>(
            success = false,
            data = null,
            error = PaymentError(
                code = HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                message = ex.message ?: "Unexpected error occurred"
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<PaymentResponse<Nothing>> {
        val errorResponse =
            PaymentResponse<Nothing>(
                success = false,
                data = null,
                error =
                    PaymentError(
                        code = HttpStatus.BAD_REQUEST.toString(),
                        message = ex.message ?: "Invalid request"
                    )
            )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
