package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.TransactionDto
import java.math.BigDecimal

interface RefundPaymentUseCase {
    fun refundAll(paymentKey: String): TransactionDto

    fun refundPartial(
        paymentKey: String,
        amount: BigDecimal
    ): TransactionDto
}
