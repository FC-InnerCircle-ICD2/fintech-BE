package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentApproveDto
import java.math.BigDecimal

fun interface SavePaymentApproveUseCase {
    data class Request(
        val paymentKey: String,
        val orderId: String,
        val amount: BigDecimal
    )

    fun saveApprove(request: Request): PaymentApproveDto
}
