package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentWithTransactionsDto

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByAccountIdRequest(
        val accountId: Long,
        val page: Int,
        val limit: Int
    )

    data class FindByPaymentKeyRequest(
        val accountId: Long,
        val paymentKey: String
    )

    fun findAllByAccountId(request: FindAllByAccountIdRequest): List<PaymentWithTransactionsDto>

    fun findByPaymentKey(request: FindByPaymentKeyRequest): PaymentWithTransactionsDto
}
