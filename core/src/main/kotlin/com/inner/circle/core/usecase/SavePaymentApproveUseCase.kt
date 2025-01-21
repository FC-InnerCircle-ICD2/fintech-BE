package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentDto
import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface SavePaymentApproveUseCase {
    data class Request(
        val paymentKey: String,
        val orderId: String
    )

    fun saveApprove(request: Request): PaymentDto
}
