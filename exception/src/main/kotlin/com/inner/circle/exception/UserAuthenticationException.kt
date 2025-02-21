package com.inner.circle.exception

sealed class UserAuthenticationException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class UserNotFoundException(
        override val message: String = "고객 정보를 확인할 수 없습니다.",
        override val cause: Throwable? = null
    ) : UserAuthenticationException(HttpStatus.NOT_FOUND, message, cause)

    data class UnauthorizedException(
        override val message: String = "요청을 허용할 수 없습니다.",
        override val cause: Throwable? = null
    ) : UserAuthenticationException(HttpStatus.UNAUTHORIZED, message, cause)

    data class InvalidPassword(
        override val message: String = "유효하지 않은 요청입니다.",
        override val cause: Throwable? = null
    ) : UserAuthenticationException(
            status = HttpStatus.BAD_REQUEST,
            message = message,
            cause = cause
        )
}
