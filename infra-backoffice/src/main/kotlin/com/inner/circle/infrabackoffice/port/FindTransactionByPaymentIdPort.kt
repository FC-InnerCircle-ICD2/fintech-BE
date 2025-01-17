package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto
import java.util.UUID

fun interface FindTransactionByPaymentIdPort {
    fun findByPaymentId(paymentId: UUID): List<TransactionDto>
}
