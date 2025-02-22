package com.inner.circle.exception

sealed class PaymentClaimException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class BadPaymentClaimRequestException(
        override val message: String = "잘못된 결제 요청입니다.",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidClaimAmountException(
        val orderId: String,
        override val message: String = "주문 ID ($orderId)와 관련된 요청 금액이 유효하지 않습니다.",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.BAD_REQUEST, message, cause)

    data class ClaimAlreadyExistsException(
        val orderId: String,
        override val message: String = "주문 ID ($orderId)로 결제가 진행 중입니다.",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.CONFLICT, message, cause)

    data class ClaimExpiredException(
        val orderId: String,
        override val message: String = "해당 주문 ID ($orderId) 결제는 만료 되었습니다.",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.GONE, message, cause)

    data class UnauthorizedClaimAccessException(
        val orderId: String,
        override val message: String = "요청하신 주문 ID ($orderId)로 결제할 수 있는 권한이 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentClaimException(HttpStatus.UNAUTHORIZED, message, cause)
}
