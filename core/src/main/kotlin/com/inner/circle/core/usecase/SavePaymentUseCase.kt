package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentDto
import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface SavePaymentUseCase {
    data class Request(
        val paymentKey: String,
        val currency: String,
        val userId: String,
        val merchantId: String,
        val paymentType: String,
        val orderId: String,
        val orderName: String
    )

    fun save(request: Request): PaymentDto
}
