package com.inner.circle.exception

sealed class PaymentException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class OrderNotFoundException(
        val orderId: String,
        override val message: String = "Order with ID $orderId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentNotFoundException(
        val paymentId: String,
        override val message: String = "Payment with ID $paymentId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class UserIdNotFoundException(
        val userId: Long?,
        override val message: String = "UserId with ID $userId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)
}
