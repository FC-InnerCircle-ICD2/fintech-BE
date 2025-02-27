package com.inner.circle.exception

import java.math.BigDecimal

sealed class PaymentException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class OrderNotFoundException(
        val orderId: String,
        override val message: String = "주문 ID ($orderId)로 결제 정보가 확인되지 않습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentNotFoundException(
        val paymentId: String,
        override val message: String = "요청된 결제 정보를 찾을 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class MerchantNotFoundException(
        val merchantId: Long,
        override val message: String = "가맹점 정보를 찾을 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class InvalidAmountException(
        val paymentKey: String,
        override val message: String = "승인 요청($paymentKey)의 결제 금액이 요청 금액과 다릅니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentRequestNotFoundException(
        val paymentKey: String,
        override val message: String = "결제 요청을 확인할 수 없습니다. $paymentKey",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentNotSaveException(
        val paymentKey: String,
        override val message: String = "결제 처리 중 오류가 발생했습니다. ($paymentKey)",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.INTERNAL_SERVER_ERROR, message, cause)

    data class PaymentKeyNotFoundException(
        override val message: String = "결제 정보를 찾을 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class CardNotFoundException(
        override val message: String = "유효한 결제수단을 찾을 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.PAYMENT_METHOD_NOT_FOUND, message, cause)

    data class CardAuthFailException(
        override val message: String = "네트워크 이슈로 결제수단이 승인되지 못했습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.PAYMENT_METHOD_NOT_VERIFIED, message, cause)

    data class InvalidOrderStatusException(
        val orderStatus: String,
        override val message: String = "주문 상태 ($orderStatus)에서는 처리될 수 없는 요청입니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class TransactionNotFoundException(
        val transactionId: String,
        override val message: String = "거래 내역 정보($transactionId)를 찾을 수 없습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class ExceedCancelAmountException(
        val paymentKey: String,
        val amount: BigDecimal,
        override val message: String = "취소 금액이 $amount 를 초과할 수 없습니다. (요청 : $paymentKey)",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class BadCancelAmountException(
        val paymentKey: String,
        val amount: BigDecimal,
        override val message: String = "취소 금액은 양수여야 합니다. (요청 : $paymentKey, $amount)",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidParameterRequestException(
        val parameterName: String?,
        override val message: String = "$parameterName 파라미터 입력이 올바르지 않습니다.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)
}
