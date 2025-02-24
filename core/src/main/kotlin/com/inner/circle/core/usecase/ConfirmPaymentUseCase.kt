package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto

interface ConfirmPaymentUseCase : ConfirmSimplePaymentUseCase {
    data class Request(
        val orderId: String,
        val merchantId: Long,
        val cardNumber: String,
        val expirationPeriod: String,
        val cvc: String,
        val cardCompany: String
    )

    fun confirmPayment(request: Request): ConfirmPaymentCoreDto
}
