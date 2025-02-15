package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.CancelPaymentDto

interface CancelPaymentUseCase {
    data class Request(
        val orderId: String,
        val merchantId: String,
        val accountId: Long
    )

    fun cancelPayment(request: Request): CancelPaymentDto
}
