package com.inner.circle.exception

sealed class PaymentJwtException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : PaymentException(status, message, cause) {
    data class TokenNotFoundException(
        override val message: String = "Payment token not found",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.NOT_FOUND, message, cause)

    data class TokenExpiredException(
        override val message: String = "Payment token has expired",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.GONE, message, cause)

    data class TokenInvalidException(
        override val message: String = "Payment token is invalid",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.UNAUTHORIZED, message, cause)

    data class TokenSignatureException(
        override val message: String = "Payment token has an invalid signature",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.UNAUTHORIZED, message, cause)

    data class ClaimExtractionException(
        val claimName: String,
        override val message: String = "Error extracting claim: $claimName",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.BAD_REQUEST, message, cause)
}
