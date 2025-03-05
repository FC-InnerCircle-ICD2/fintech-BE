package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.TransactionDto
import com.inner.circle.infra.port.GetTransactionPort
import com.inner.circle.infra.port.SaveTransactionPort
import com.inner.circle.infra.repository.TransactionRepository
import com.inner.circle.infra.repository.entity.TransactionEntity
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : SaveTransactionPort,
    GetTransactionPort {
    override fun save(request: SaveTransactionPort.Request): TransactionDto {
        val result =
            transactionRepository.save(
                TransactionEntity(
                    id = request.id,
                    paymentKey = request.paymentKey,
                    amount = request.amount,
                    status = request.status,
                    reason = request.reason,
                    requestedAt = request.requestedAt
                )
            )

        return TransactionDto(
            id = requireNotNull(result.id),
            paymentKey = result.paymentKey,
            amount = result.amount,
            status = result.status,
            reason = result.reason,
            requestedAt = result.requestedAt.toKotlinLocalDateTime(),
            createdAt = result.createdAt.toKotlinLocalDateTime(),
            updatedAt = result.updatedAt.toKotlinLocalDateTime()
        )
    }

    override fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionDto> =
        transactionRepository
            .findAllByPaymentKeyInOrderByCreatedAtDesc(paymentKeys)
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
            .findAllByPaymentKeyOrderByCreatedAtDesc(request.paymentKey)
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
