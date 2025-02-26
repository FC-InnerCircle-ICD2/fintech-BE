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

    data class InvalidParameterRequestException(
        val parameterName: String?,
        override val message: String = "$parameterName 파라미터 입력이 올바르지 않습니다.",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.BAD_REQUEST, message, cause)
}
