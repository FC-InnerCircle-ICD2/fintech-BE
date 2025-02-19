package com.inner.circle.core.usecase

import com.inner.circle.core.domain.TransactionStatus
import com.inner.circle.core.service.dto.PaymentWithTransactionsDto
import kotlinx.datetime.LocalDate

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByAccountIdRequest(
        val accountId: Long,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val status: TransactionStatus?,
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
