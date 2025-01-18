package com.inner.circle.infrabackoffice.port.transaction

import com.inner.circle.infrabackoffice.adaptor.dto.transaction.TransactionDto
import java.util.UUID

fun interface FindByPaymentIdPort {
    fun findByPaymentId(paymentId: UUID): List<TransactionDto>
}
