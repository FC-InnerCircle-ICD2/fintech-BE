package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto

interface GetTransactionPort {
    data class Request(
        val paymentKey: String
    )

    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionDto>

    fun findAllByPaymentKey(request: Request): List<TransactionDto>
}
