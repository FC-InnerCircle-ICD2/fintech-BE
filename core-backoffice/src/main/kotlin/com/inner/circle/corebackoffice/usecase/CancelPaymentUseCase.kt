package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.TransactionDto
import java.math.BigDecimal

fun interface CancelPaymentUseCase {
    data class CancelPaymentRequest(
        val merchantId: Long,
        val paymentKey: String,
        val amount: BigDecimal
    ) {
        init {
            require(amount > BigDecimal.ZERO) { "amount must be greater than zero" }
        }
    }

    fun cancel(request: CancelPaymentRequest): TransactionDto
}
