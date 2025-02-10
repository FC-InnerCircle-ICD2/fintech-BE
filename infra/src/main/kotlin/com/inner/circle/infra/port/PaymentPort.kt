package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.PaymentEntity
import com.inner.circle.infra.repository.entity.PaymentType

fun interface PaymentPort {
    data class Request(
        val id: Long?,
        val paymentKey: String,
        val currency: String,
        val accountId: Long?,
        val merchantId: String,
        val paymentType: PaymentType,
        val orderId: String,
        val orderName: String?,
        val cardNumber: String
    )

    fun save(request: Request): PaymentEntity
}
