package com.inner.circle.exception

sealed class PaymentJwtException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : PaymentException(status, message, cause) {
    data class TokenNotFoundException(
        override val message: String = "결제 토큰 정보를 확인할 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.NOT_FOUND, message, cause)

    data class TokenExpiredException(
        override val message: String = "요청한 결제 토큰은 만료되었습니다.",
        override val cause: Throwable? = null
    ) : PaymentJwtException(HttpStatus.GONE, message, cause)

    data class TokenInvalidException(
        override val message: String = "결제 토큰이 유효하지 않습니다.",
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
