package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.TransactionDto
import com.inner.circle.exception.PaymentException
import java.math.BigDecimal

interface CancelPaymentUseCase {
    data class CancelPaymentRequest(
        val accountId: Long,
        val paymentKey: String,
        val amount: BigDecimal
    ) {
        init {
            require(amount > BigDecimal.ZERO) {
                throw PaymentException.BadCancelAmountException(
                    paymentKey = paymentKey,
                    amount = amount
                )
            }
        }
    }

    fun cancel(request: CancelPaymentRequest): TransactionDto
}
