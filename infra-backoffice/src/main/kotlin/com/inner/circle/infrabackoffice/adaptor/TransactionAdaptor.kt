package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import com.inner.circle.infrabackoffice.port.SaveTransactionPort
import com.inner.circle.infrabackoffice.repository.TransactionRepository
import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : GetTransactionPort,
    SaveTransactionPort {
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

    @Transactional
    override fun save(request: SaveTransactionPort.Request): TransactionDto =
        transactionRepository
            .save(
                TransactionEntity(
                    id = request.id,
                    paymentKey = request.paymentKey,
                    amount = request.amount,
                    status = request.status,
                    reason = request.reason,
                    requestedAt = request.requestedAt.toJavaLocalDateTime()
                )
            ).let { transaction ->
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
            }
}
