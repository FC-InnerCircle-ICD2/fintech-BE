package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.PaymentDto
import com.inner.circle.corebackoffice.service.dto.TransactionDto

interface ResponsePaymentUseCase {
    data class Request(
        val paymentKey: String
    )

    fun getPayment(request: Request): PaymentDto

    fun getTransactions(request: Request): List<TransactionDto>
}
