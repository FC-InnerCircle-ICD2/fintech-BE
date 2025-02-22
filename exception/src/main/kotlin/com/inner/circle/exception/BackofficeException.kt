package com.inner.circle.exception

sealed class BackofficeException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class MerchantNotSaveException(
        override val message: String = "Merchant not save",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.NOT_FOUND, message, cause)

    data class MerchantAlreadyExistException(
        override val message: String = "이미 가입된 회원입니다.",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.NOT_FOUND, message, cause)
}
