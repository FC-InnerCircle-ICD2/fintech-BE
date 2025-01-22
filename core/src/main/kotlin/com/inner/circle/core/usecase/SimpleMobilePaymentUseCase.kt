package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.MobilePaymentCoreDto

interface SimpleMobilePaymentUseCase {
    data class Request(
        val orderId: String
    )

    fun confirmPayment(request: Request): MobilePaymentCoreDto
}
