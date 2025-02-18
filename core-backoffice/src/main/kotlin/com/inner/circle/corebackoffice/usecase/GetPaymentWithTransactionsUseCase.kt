package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.PaymentWithTransactionsDto

interface GetPaymentWithTransactionsUseCase {
    data class FindAllByMerchantIdRequest(
        val merchantId: Long,
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
