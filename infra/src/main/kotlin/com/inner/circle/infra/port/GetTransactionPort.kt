package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.TransactionDto

fun interface GetTransactionPort {
    data class Request(
        val paymentKey: String
    )

    fun findAllByPaymentKey(request: Request): List<TransactionDto>
}
