package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface GetPaymentPort {
    data class Request(
        val accountId: Long,
        val paymentKey: String
    )

    fun findByAccountIdAndPaymentKey(request: Request): PaymentEntity
}
