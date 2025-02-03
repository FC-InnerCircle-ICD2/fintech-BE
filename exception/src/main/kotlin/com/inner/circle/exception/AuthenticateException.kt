package com.inner.circle.exception

sealed class AuthenticateException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class CardAuthFailException(
        override val message: String = "This card cannot be authenticated.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class CardNotFoundException(
        override val message: String
        = "Simple payment is not possible because card information does not exist.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)
}
