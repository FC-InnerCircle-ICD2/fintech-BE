package com.inner.circle.exception

import java.math.BigDecimal

sealed class PaymentException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class OrderNotFoundException(
        val orderId: String,
        override val message: String = "Order with ID $orderId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class AccountNotFoundException(
        val accountId: Long?,
        override val message: String = "Account with ID $accountId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentNotFoundException(
        val paymentId: String,
        override val message: String = "Payment with ID $paymentId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class MerchantNotFoundException(
        val merchantId: Long,
        override val message: String = "Merchant with ID $merchantId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class InvalidAmountException(
        val paymentKey: String,
        override val message: String = "PaymentKey $paymentKey order payment amount is different.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentRequestNotFoundException(
        val paymentKey: String,
        override val message: String = "Payment Request not found : $paymentKey",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentNotSaveException(
        val paymentKey: String,
        override val message: String = "Payment not save : $paymentKey",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class PaymentKeyNotFoundException(
        override val message: String = "PaymentKey not found.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class CardNotFoundException(
        override val message: String = "Payment method (card) not found.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class InvalidOrderStatusException(
        val orderStatus: String,
        override val message: String = "$orderStatus is not in IN_PROGRESS state.",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class TransactionNotFoundException(
        val transactionId: String,
        override val message: String = "Transaction with ID $transactionId not found",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.NOT_FOUND, message, cause)

    data class AlreadyRefundException(
        val paymentKey: String,
        override val message: String = "Already got a full refund : $paymentKey",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)

    data class ExceedRefundAmountException(
        val paymentKey: String,
        val amount: BigDecimal,
        override val message: String = "No refunds for amounts exceeding $amount. : $paymentKey",
        override val cause: Throwable? = null
    ) : PaymentException(HttpStatus.BAD_REQUEST, message, cause)
}
