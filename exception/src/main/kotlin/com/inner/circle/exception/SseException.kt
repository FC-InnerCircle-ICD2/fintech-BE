package com.inner.circle.exception

sealed class SseException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class ConnectionNotFoundException(
        val merchantId: String,
        val orderId: String,
        override val message: String =
            "SSE connection not found for merchantId: $merchantId, orderId: $orderId",
        override val cause: Throwable? = null
    ) : SseException(HttpStatus.NOT_FOUND, message, cause)
}
