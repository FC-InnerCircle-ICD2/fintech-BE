package com.inner.circle.apibackoffice.exception

import com.inner.circle.apibackoffice.common.response.AppExceptionResponse
import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.exception.AppException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val errorLogger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(AppException::class)
    fun handleAppException(exception: AppException): ResponseEntity<AppExceptionResponse> {
        errorLogger.error("AppException (type = {})", exception::class.simpleName, exception)

        return ResponseEntity(
            AppExceptionResponse.of(exception),
            HttpStatus.valueOf(exception.status.code)
        )
    }

    @ExceptionHandler(Exception::class)
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected error",
        content = [Content(schema = Schema(implementation = BackofficeResponse::class))]
    )
    fun handleException(ex: Exception): ResponseEntity<BackofficeResponse<Any>> {
        errorLogger.error("An unexpected error occurred", ex)
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(status.toString(), "Unexpected error occurred.")
            )
        return ResponseEntity(errorResponse, status)
    }

    @ExceptionHandler(IllegalArgumentException::class, ConstraintViolationException::class)
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request",
        content = [Content(schema = Schema(implementation = BackofficeResponse::class))]
    )
    fun handleIllegalArgumentException(ex: Exception): ResponseEntity<BackofficeResponse<Any>> {
        errorLogger.error("(type = {})", ex::class.simpleName, ex)
        val errorMessage =
            if (ex is ConstraintViolationException) {
                "유효하지 않은 요청입니다. ${ex.message.orEmpty()}".trimEnd()
            } else {
                "유효하지 않은 요청입니다."
            }
        val status = HttpStatus.BAD_REQUEST
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(status.toString(), errorMessage)
            )
        return ResponseEntity(errorResponse, status)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.error("MethodArgumentNotValidException (type = {})", ex::class.simpleName, ex)
        return ResponseEntity.badRequest().body(
            BackofficeResponse.fail(
                error =
                    BackofficeError(
                        code = HttpStatus.BAD_REQUEST.name,
                        message =
                            ex.bindingResult.allErrors
                                .first()
                                .defaultMessage ?: "유효하지 않은 요청입니다."
                    )
            )
        )
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.info(
            "HttpRequestMethodNotSupportedException (type = {})",
            ex::class.simpleName,
            ex
        )
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(
                    code = com.inner.circle.exception.HttpStatus.METHOD_NOT_ALLOWED.name,
                    message = "요청하신 주소는 ${ex.method} 를 지원하지 않습니다."
                )
            )
        return ResponseEntity(errorResponse, HttpStatus.METHOD_NOT_ALLOWED)
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.error(
            "HttpMediaTypeNotSupportedException (type = {})",
            ex::class.simpleName,
            ex
        )
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(
                    code = com.inner.circle.exception.HttpStatus.UNSUPPORTED_MEDIA_TYPE.name,
                    message = ex.contentType?.let { "지원되지 않는 요청입니다. ($it)" } ?: "지원되지 않는 요청입니다."
                )
            )
        return ResponseEntity(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.error(
            "HttpMessageNotReadableException (type = {})",
            ex::class.simpleName,
            ex
        )

        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(
                    code = com.inner.circle.exception.HttpStatus.BAD_REQUEST.name,
                    message = "유효하지 않은 request 이므로 처리되지 못했습니다."
                )
            )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    override fun handleNoResourceFoundException(
        ex: NoResourceFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.info("NoResourceFoundException (type = {})", ex::class.simpleName, ex)
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(
                    code = com.inner.circle.exception.HttpStatus.NOT_FOUND.name,
                    message = "${ex.resourcePath} 는 유효하지 않은 경로입니다."
                )
            )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        errorLogger.info("httpMediaTypeNotAcceptable (type = {})", ex::class.simpleName, ex)
        val errorResponse =
            BackofficeResponse.fail(
                BackofficeError(
                    code = com.inner.circle.exception.HttpStatus.BAD_REQUEST.name,
                    message = "허용되지 않는 요청입니다."
                )
            )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
