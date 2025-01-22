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

    data class AmountValidationException(
        val paymentKey: String,
        override val message: String = "PaymentKey $paymentKey order payment amount is different.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentRequestNotFoundException(
        val paymentKey: String,
        val orderId: String,
        override val message: String = "Payment Request not found - payment_key: $paymentKey, order_id: $orderId",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)
}
