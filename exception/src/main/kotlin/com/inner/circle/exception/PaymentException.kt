package com.inner.circle.exception

sealed class PaymentException(
    status: HttpStatus,
    override val message: String,
    cause: Throwable? = null
) : AppException(status, message, cause) {

    data class OrderNotFoundException(
        val orderId: String,
        override val message: String = "Order with ID $orderId not found",
        override val cause: Throwable? = null,
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)
}
