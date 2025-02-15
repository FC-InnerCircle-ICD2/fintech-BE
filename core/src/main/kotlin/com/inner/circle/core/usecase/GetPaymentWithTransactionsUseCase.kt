package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentWithTransactionsDto

fun interface GetPaymentWithTransactionsUseCase {
    data class Request(
        val accountId: Long,
        val paymentKey: String
    )

    fun findByPaymentKey(request: Request): PaymentWithTransactionsDto
}
