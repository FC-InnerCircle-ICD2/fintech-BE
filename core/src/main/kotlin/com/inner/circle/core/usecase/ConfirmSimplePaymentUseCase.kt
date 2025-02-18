package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto

fun interface ConfirmSimplePaymentUseCase {
    data class Request(
        val orderId: String,
        val merchantId: Long,
        val accountId: Long
    )

    fun confirmPayment(request: Request): ConfirmPaymentCoreDto
}
