package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.PaymentTransaction
import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.TransactionDto
import com.inner.circle.corebackoffice.usecase.ResponseTransactionUseCase
import com.inner.circle.infrabackoffice.port.transaction.FindByPaymentIdPort
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val findByPaymentIdPort: FindByPaymentIdPort
) : ResponseTransactionUseCase {
    override fun getTransactions(
        request: ResponseTransactionUseCase.Request
    ): List<TransactionDto> {
        val transactions =
            findByPaymentIdPort
                .findByPaymentId(request.paymentId)
                .map { transaction ->
                    PaymentTransaction(
                        transaction.id,
                        transaction.paymentId,
                        transaction.amount,
                        TransactionStatus.of(transaction.status),
                        transaction.reason,
                        transaction.requestedAt,
                        transaction.completedAt,
                        transaction.createdAt,
                        transaction.updatedAt
                    )
                }.toList()

        return transactions.map { transaction ->
            TransactionDto.from(transaction)
        }
    }
}
