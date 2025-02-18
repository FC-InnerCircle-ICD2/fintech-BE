package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentType

data class PaymentWithTransactionsDto(
    val paymentKey: String,
    val cardNumber: String,
    val accountId: Long?,
    val transactions: List<TransactionDto>,
    val paymentType: PaymentType,
    val orderId: String,
    val orderName: String?
)
