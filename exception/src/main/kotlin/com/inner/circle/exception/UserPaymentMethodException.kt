package com.inner.circle.exception

sealed class UserPaymentMethodException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class InvalidCardNumberException(
        val cardNumber: String,
        override val message: String = "Invalid card number: $cardNumber",
        override val cause: Throwable? = null
    ) : UserPaymentMethodException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidCVCException(
        override val message: String = "Invalid CVC value.",
        override val cause: Throwable? = null
    ) : UserPaymentMethodException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidExpirationDateException(
        val expirationDate: String,
        override val message: String = "Invalid expiration date: $expirationDate",
        override val cause: Throwable? = null
    ) : UserPaymentMethodException(HttpStatus.BAD_REQUEST, message, cause)
}
