package com.inner.circle.api.controller.dto

import com.inner.circle.core.service.dto.PaymentWithTransactionsDto as CorePaymentWithTransactionsDto

data class PaymentWithTransactionsDto(
    val paymentKey: String,
    val cardNumber: String,
    val accountId: Long?,
    val transactions: List<TransactionDto>,
    val paymentType: PaymentType,
    val orderId: String,
    val orderName: String?
) {
    companion object {
        fun of(
            paymentWithTransactions: CorePaymentWithTransactionsDto
        ): PaymentWithTransactionsDto =
            PaymentWithTransactionsDto(
                paymentKey = paymentWithTransactions.paymentKey,
                cardNumber = paymentWithTransactions.cardNumber,
                accountId = paymentWithTransactions.accountId,
                transactions = paymentWithTransactions.transactions.map { TransactionDto.of(it) },
                paymentType = PaymentType.of(paymentWithTransactions.paymentType),
                orderId = paymentWithTransactions.orderId,
                orderName = paymentWithTransactions.orderName
            )
    }
}
