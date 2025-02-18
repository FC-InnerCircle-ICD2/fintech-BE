package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.TransactionDto

interface GetTransactionPort {
    data class Request(
        val paymentKey: String
    )

    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionDto>

    fun findAllByPaymentKey(request: Request): List<TransactionDto>
}
