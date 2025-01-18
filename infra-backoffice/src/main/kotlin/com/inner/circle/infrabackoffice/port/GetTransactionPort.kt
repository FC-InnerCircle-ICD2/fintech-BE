package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto

fun interface GetTransactionPort {
    data class Request(
        val paymentKey: String
    )

    fun getTransactionsByPaymentKey(request: Request): List<TransactionDto>
}
