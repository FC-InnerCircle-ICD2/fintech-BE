package com.inner.circle.exception

sealed class UserCardException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class BadCardNumberException(
        val cardNumber: String,
        override val message: String = "잘못된 카드 번호 형식입니다. (카드 번호: $cardNumber)",
        override val cause: Throwable? = null
    ) : UserCardException(HttpStatus.BAD_REQUEST, message, cause)

    data class AlreadyRegisterCardException(
        val cardNumber: String,
        override val message: String = "이미 등록한 카드입니다. (카드 번호: $cardNumber)",
        override val cause: Throwable? = null
    ) : UserCardException(HttpStatus.CONFLICT, message, cause)

    data class CardNotFoundException(
        val id: Long,
        override val message: String = "요청하신 카드는 존재하지 않은 카드입니다. (카드 id: $id)",
        override val cause: Throwable? = null
    ) : UserCardException(HttpStatus.BAD_REQUEST, message, cause)
}
