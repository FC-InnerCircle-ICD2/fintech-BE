package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.PaymentWithTransactionsDto
import kotlinx.datetime.LocalDate

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByMerchantIdRequest(
        val merchantId: Long,
        val paymentKey: String?,
        val status: TransactionStatus?,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val page: Int,
        val limit: Int
    )

    data class FindByPaymentKeyRequest(
        val merchantId: Long,
        val paymentKey: String
    )

    fun findAllByMerchantId(request: FindAllByMerchantIdRequest): List<PaymentWithTransactionsDto>

    fun findByPaymentKey(request: FindByPaymentKeyRequest): PaymentWithTransactionsDto
}
