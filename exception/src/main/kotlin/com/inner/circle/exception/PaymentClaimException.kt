package com.inner.circle.exception

sealed class PaymentClaimException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class BadPaymentClaimRequestException(
        override val message: String = "Bad payment claim request",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidClaimAmountException(
        val orderId: String,
        override val message: String = "Claim with OrderId ($orderId) has an invalid amount",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.BAD_REQUEST, message, cause)

    data class ClaimAlreadyExistsException(
        val orderId: String,
        override val message: String = "Claim with OrderId ($orderId) already exists",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.CONFLICT, message, cause)

    data class ClaimExpiredException(
        val orderId: String,
        override val message: String = "Claim with OrderId ($orderId) has expired",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.GONE, message, cause)

    data class UnauthorizedClaimAccessException(
        val orderId: String,
        override val message: String = "Unauthorized access to claim with OrderId ($orderId)",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.UNAUTHORIZED, message, cause)
}
