package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.TransactionDto
import com.inner.circle.infra.port.GetTransactionPort
import com.inner.circle.infra.port.TransactionPort
import com.inner.circle.infra.repository.TransactionRepository
import com.inner.circle.infra.repository.entity.TransactionEntity
import java.time.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository
) : TransactionPort,
    GetTransactionPort {
    override fun save(request: TransactionPort.Request) {
        transactionRepository.save(
            TransactionEntity(
                id = request.id,
                paymentKey = request.paymentKey,
                amount = request.amount,
                status = request.status,
                reason = request.reason,
                // TODO: 추후 paymentRequest의 생성 시각을 넣어줘야함.
                requestedAt = LocalDateTime.now()
            )
        ) ?: throw IllegalArgumentException(
            "Payment Transaction not save"
        )
    }

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
