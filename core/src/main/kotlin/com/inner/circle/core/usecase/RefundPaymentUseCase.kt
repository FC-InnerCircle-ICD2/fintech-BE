package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.TransactionDto
import java.math.BigDecimal

interface RefundPaymentUseCase {
    fun refundAll(
        accountId: Long,
        paymentKey: String
    ): TransactionDto

    fun refundPartial(
        accountId: Long,
        paymentKey: String,
        amount: BigDecimal
    ): TransactionDto
}
