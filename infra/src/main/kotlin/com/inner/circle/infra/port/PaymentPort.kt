package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface PaymentPort {
    data class Request(
        val paymentKey: String,
        val currency: String,
        val userId: Long?,
        val merchantId: String,
        val paymentType: String,
        val orderId: String,
        val orderName: String?
    )

    fun save(request: Request): PaymentEntity
}
