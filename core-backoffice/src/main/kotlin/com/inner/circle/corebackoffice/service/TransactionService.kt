package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.PaymentTransaction
import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.TransactionDto
import com.inner.circle.corebackoffice.usecase.GetTransactionUseCase
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val getTransactionPort: GetTransactionPort
) : GetTransactionUseCase {
    override fun getTransactionsByPaymentKey(
        request: GetTransactionUseCase.Request
    ): List<TransactionDto> {
        val transactions =
            getTransactionPort
                .getTransactionsByPaymentKey(
                    GetTransactionPort.Request(
                        paymentKey = request.paymentKey
                    )
                ).map { transaction ->
                    PaymentTransaction(
                        id = transaction.id,
                        paymentId = transaction.paymentId,
                        amount = transaction.amount,
                        status = TransactionStatus.of(transaction.status),
                        reason = transaction.reason,
                        requestedAt = transaction.requestedAt,
                        completedAt = transaction.completedAt,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt
                    )
                }.toList()

        return transactions.map { transaction ->
            TransactionDto.of(transaction)
        }
    }
}
