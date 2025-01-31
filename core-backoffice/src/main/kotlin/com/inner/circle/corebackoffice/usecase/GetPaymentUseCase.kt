package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.PaymentDto

fun interface GetPaymentUseCase {
    data class Request(
        val paymentKey: String
    )

    fun getPaymentByPaymentKey(request: Request): PaymentDto
}
