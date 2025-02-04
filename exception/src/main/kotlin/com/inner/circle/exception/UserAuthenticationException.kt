package com.inner.circle.exception

sealed class UserAuthenticationException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class UserNotFoundException(
        override val message: String = "Request User not found.",
        override val cause: Throwable? = null
    ) : UserAuthenticationException(HttpStatus.NOT_FOUND, message, cause)
}