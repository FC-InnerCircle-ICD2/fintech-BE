package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.TransactionDto

fun interface GetTransactionUseCase {
    data class Request(
        val paymentKey: String
    )

    fun getTransactionsByPaymentKey(request: Request): List<TransactionDto>
}
