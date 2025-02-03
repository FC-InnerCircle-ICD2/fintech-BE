package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface PaymentPort {
    data class Request(
        val id: Long?,
        val paymentKey: String,
        val currency: String,
        val accountId: Long?,
        val merchantId: String,
        val paymentType: String,
        val orderId: String,
        val orderName: String?
    )

    fun save(request: Request): PaymentEntity
}
