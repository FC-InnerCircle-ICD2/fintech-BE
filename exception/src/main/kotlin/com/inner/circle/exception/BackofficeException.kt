package com.inner.circle.exception

import java.math.BigDecimal

sealed class BackofficeException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class MerchantAlreadyExistException(
        override val message: String = "이미 가입된 회원입니다.",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.NOT_FOUND, message, cause)

    data class InvalidParameterRequestException(
        val parameterName: String?,
        override val message: String = "$parameterName 파라미터 입력이 올바르지 않습니다.",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.BAD_REQUEST, message, cause)

    data class ExceedCancelAmountException(
        val paymentKey: String,
        val amount: BigDecimal,
        override val message: String = "취소 금액이 $amount 를 초과할 수 없습니다. (요청 : $paymentKey)",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.BAD_REQUEST, message, cause)

    data class BadCancelAmountException(
        val paymentKey: String,
        val amount: BigDecimal,
        override val message: String = "취소 금액은 양수여야 합니다. (요청 : $paymentKey, $amount)",
        override val cause: Throwable? = null
    ) : BackofficeException(HttpStatus.BAD_REQUEST, message, cause)
}
