package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentRequestDto

fun interface GetPaymentRequestUseCase {
    data class Request(
        val paymentKey: String
    )

    fun getPaymentRequest(request: Request): PaymentRequestDto
}
