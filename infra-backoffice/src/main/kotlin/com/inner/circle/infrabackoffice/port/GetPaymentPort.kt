package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import kotlinx.datetime.LocalDate

interface GetPaymentPort {
    data class FindAllByMerchantIdRequest(
        val merchantId: Long,
        val paymentKey: String?,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val page: Int,
        val limit: Int
    )

    data class FindByPaymentKeyRequest(
        val merchantId: Long,
        val paymentKey: String
    )

    fun findAllByMerchantId(request: FindAllByMerchantIdRequest): List<PaymentDto>

    fun findByMerchantIdAndPaymentKey(request: FindByPaymentKeyRequest): PaymentDto
}
