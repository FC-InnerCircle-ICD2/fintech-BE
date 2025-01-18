package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.TransactionDto
import java.util.UUID

fun interface ResponseTransactionUseCase {
    data class Request(
        val paymentId: UUID
    )

    fun getTransactions(request: Request): List<TransactionDto>
}
