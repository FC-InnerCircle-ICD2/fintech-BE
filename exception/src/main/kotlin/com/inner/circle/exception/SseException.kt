package com.inner.circle.exception

class SseException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    companion object {
        fun connectionNotFound(
            merchantId: String,
            orderId: String
        ): SseException =
            SseException(
                HttpStatus.NOT_FOUND,
                "SSE connection not found for merchantId: $merchantId, orderId: $orderId"
            )
    }
}
