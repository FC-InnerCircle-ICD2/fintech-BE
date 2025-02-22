package com.inner.circle.exception

sealed class AuthenticateException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class CardAuthFailException(
        val cardNumber: String?,
        override val message: String =
            "카드 ${if (cardNumber.isNullOrBlank()) "" else "($cardNumber)"} 인증 과정에서 오류가 발생했습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class CardInformationNotFoundException(
        override val message: String =
            "결제 처리에 필요한 카드 정보가 확인되지 않습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)
}
