package com.inner.circle.core.structure.usecase

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto

interface SimpleMobilePaymentUseCase {
    data class Request(
        val orderId: String
    )

    fun confirmPayment(request: Request): MobilePaymentCoreDto
}

