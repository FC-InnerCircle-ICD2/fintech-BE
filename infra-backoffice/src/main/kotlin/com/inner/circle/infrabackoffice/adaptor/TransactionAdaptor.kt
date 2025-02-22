package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import com.inner.circle.infrabackoffice.repository.TransactionRepository
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : GetTransactionPort {
    override fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionDto> =
        transactionRepository
            .findAllByPaymentKeyIn(paymentKeys)
            .map { transaction ->
                TransactionDto(
                    id = requireNotNull(transaction.id),
                    paymentKey = transaction.paymentKey,
                    amount = transaction.amount,
                    status = transaction.status,
                    reason = transaction.reason,
                    requestedAt = transaction.requestedAt.toKotlinLocalDateTime(),
                    createdAt = transaction.createdAt.toKotlinLocalDateTime(),
                    updatedAt = transaction.updatedAt.toKotlinLocalDateTime()
                )
            }.toList()

    override fun findAllByPaymentKey(request: GetTransactionPort.Request): List<TransactionDto> =
        transactionRepository
            .findAllByPaymentKey(request.paymentKey)
            .map { transaction ->
                TransactionDto(
                    id = requireNotNull(transaction.id),
                    paymentKey = transaction.paymentKey,
                    amount = transaction.amount,
                    status = transaction.status,
                    reason = transaction.reason,
                    requestedAt = transaction.requestedAt.toKotlinLocalDateTime(),
                    createdAt = transaction.createdAt.toKotlinLocalDateTime(),
                    updatedAt = transaction.updatedAt.toKotlinLocalDateTime()
                )
            }.toList()
            .ifEmpty {
                throw PaymentException.TransactionNotFoundException(
                    transactionId = "",
                    message = "Transaction with paymentKey ${request.paymentKey} not found"
                )
            }
}
