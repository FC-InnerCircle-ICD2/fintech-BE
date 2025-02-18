package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.PaymentDto

interface GetPaymentPort {
    data class FindAllByAccountIdRequest(
        val accountId: Long,
        val page: Int,
        val limit: Int
    )

    data class FindByPaymentKeyRequest(
        val accountId: Long,
        val paymentKey: String
    )

    fun findAllByAccountId(request: FindAllByAccountIdRequest): List<PaymentDto>

    fun findByAccountIdAndPaymentKey(request: FindByPaymentKeyRequest): PaymentDto
}
