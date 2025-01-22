package com.inner.circle.exception

sealed class PaymentTokenException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class TokenNotFoundException(
        val token: String,
        override val message: String = "Payment token with ID $token not found",
        override val cause: Throwable? = null
    ) : PaymentTokenException(HttpStatus.NOT_FOUND, message, cause)

    data class TokenExpiredException(
        val token: String,
        override val message: String = "Payment token with ID $token has expired",
        override val cause: Throwable? = null
    ) : PaymentTokenException(HttpStatus.GONE, message, cause)
}
