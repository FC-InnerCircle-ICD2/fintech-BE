package com.inner.circle.api.exception

import com.inner.circle.api.controller.dto.PaymentError
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.exception.AppException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import javax.security.sasl.AuthenticationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<PaymentResponse<Nothing>> =
        ResponseEntity(
            PaymentResponse.fail(
                error =
                    PaymentError(
                        code = HttpStatus.BAD_REQUEST.name,
                        message =
                            ex.bindingResult.allErrors
                                .first()
                                .defaultMessage ?: "유효하지 않은 요청입니다."
                    )
            ),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(AppException::class)
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = [Content(schema = Schema(implementation = PaymentResponse::class))]
    )
    fun handleAppException(exception: AppException): ResponseEntity<PaymentResponse<Nothing>> {
        logger.error("AppException (type = {})", exception::class.simpleName, exception)

        var code = exception.status.code
        try {
            code = HttpStatus.valueOf(code).value()
        } catch (e: Exception) {
            code = HttpStatus.BAD_REQUEST.value()
        }
        return ResponseEntity(
            PaymentResponse.fail(
                error =
                    PaymentError(
                        code = exception.status.name,
                        message = exception.message
                    )
            ),
            HttpStatus.valueOf(code)
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = [Content(schema = Schema(implementation = PaymentResponse::class))]
    )
    fun handleAuthorizationException(
        exception: AuthenticationException
    ): ResponseEntity<PaymentResponse<Nothing>> {
        logger.error("AuthenticationException (type = {})", exception::class.simpleName, exception)
        val unauthorized = com.inner.circle.exception.HttpStatus.UNAUTHORIZED
        return ResponseEntity(
            PaymentResponse.fail(
                error =
                    PaymentError(
                        code = unauthorized.name,
                        message = unauthorized.description
                    )
            ),
            HttpStatus.valueOf(unauthorized.code)
        )
    }

    @ExceptionHandler(Exception::class)
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = [Content(schema = Schema(implementation = PaymentResponse::class))]
    )
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
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request",
        content = [Content(schema = Schema(implementation = PaymentResponse::class))]
    )
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<PaymentResponse<Nothing>> {
        logger.error("IllegalArgumentException (type = {})", ex::class.simpleName, ex)
        val status = HttpStatus.BAD_REQUEST
        val errorResponse =
            PaymentResponse.fail(
                PaymentError(status.toString(), ex.message ?: "Invalid request.")
            )
        return ResponseEntity(errorResponse, status)
    }
}
