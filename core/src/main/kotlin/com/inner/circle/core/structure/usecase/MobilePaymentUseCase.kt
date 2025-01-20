package com.inner.circle.core.structure.usecase

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto

interface MobilePaymentUseCase {
    data class Request(
        val orderId: String,
        val userId: Long,
        val representativeYn: Boolean,
        val cardNumber: String,
        val expirationPeriod: String?,
        val cvc: String?
    )

    fun confirmPayment(request: Request): MobilePaymentCoreDto
}
