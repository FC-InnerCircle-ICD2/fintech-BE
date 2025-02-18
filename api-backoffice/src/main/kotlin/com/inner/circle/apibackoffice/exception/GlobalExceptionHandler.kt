package com.inner.circle.apibackoffice.exception

import com.inner.circle.apibackoffice.common.response.AppExceptionResponse
import com.inner.circle.exception.AppException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AppException::class)
    fun handleAppException(exception: AppException): ResponseEntity<AppExceptionResponse> =
        ResponseEntity(
            AppExceptionResponse.of(exception),
            HttpStatus.valueOf(exception.status.code)
        )

    @ExceptionHandler(Exception::class)
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = [Content(schema = Schema(implementation = BackofficeResponse::class))]
    )
    fun handleException(ex: Exception): ResponseEntity<BackofficeResponse<Nothing>> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(status.toString(), "Unexpected error occurred.")
            )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request",
        content = [Content(schema = Schema(implementation = BackofficeResponse::class))]
    )
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<BackofficeResponse<Nothing>> {
        val status = HttpStatus.BAD_REQUEST
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(status.toString(), "Invalid request")
            )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<BackofficeResponse<Nothing>> =
        ResponseEntity(
            BackofficeResponse.fail(
                error =
                    BackofficeError(
                        code = HttpStatus.BAD_REQUEST.toString(),
                        message =
                            ex.bindingResult.allErrors
                                .first()
                                .defaultMessage ?: "Invalid Argument"
                    )
            ),
            HttpStatus.BAD_REQUEST
        )
}
