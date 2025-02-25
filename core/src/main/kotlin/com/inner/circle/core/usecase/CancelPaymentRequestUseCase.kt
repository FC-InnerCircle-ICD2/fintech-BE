package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.CancelPaymentDto

fun interface CancelPaymentRequestUseCase {
    data class Request(
        val orderId: String,
        val merchantId: Long,
        val accountId: Long
    )

    fun cancel(request: Request): CancelPaymentDto
}
