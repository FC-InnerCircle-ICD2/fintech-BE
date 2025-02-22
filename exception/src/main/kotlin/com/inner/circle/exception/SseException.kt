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
            "SSE 연결 정보를 확인할 수 없습니다. (merchantId: $merchantId, orderId: $orderId)",
        override val cause: Throwable? = null
    ) : SseException(HttpStatus.NOT_FOUND, message, cause)

    data class EndOfPaymentProgressException(
        override val message: String = "결제 처리가 완료된 상태입니다.",
        override val cause: Throwable? = null
    ) : SseException(HttpStatus.BAD_REQUEST, message, cause)
}
