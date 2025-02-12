package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto

interface ConfirmSimplePaymentUseCase {
    data class Request(
        val orderId: String,
        val merchantId: Long
    )

    fun confirmPayment(request: Request): ConfirmPaymentCoreDto
}
