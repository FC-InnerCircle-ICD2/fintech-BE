package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.adaptor.dto.TransactionDto
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import com.inner.circle.infrabackoffice.repository.PaymentRepository
import com.inner.circle.infrabackoffice.repository.TransactionRepository
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class TransactionAdaptor(
    private val transactionRepository: TransactionRepository,
    private val paymentRepository: PaymentRepository
) : GetTransactionPort {
    override fun getTransactionsByPaymentKey(
        request: GetTransactionPort.Request
    ): List<TransactionDto> {
        val payment =
            paymentRepository.findByPaymentKey(request.paymentKey)
                ?: throw PaymentException.PaymentNotFoundException(
                    paymentId = request.paymentKey
                )
        return transactionRepository
            .findByPaymentId(requireNotNull(payment.id))
            .map {
                TransactionDto(
                    id = requireNotNull(it.id),
                    paymentId = it.paymentId,
                    amount = it.amount,
                    status = it.status,
                    reason = it.reason,
                    requestedAt = it.requestedAt.toKotlinLocalDateTime(),
                    completedAt = it.completedAt.toKotlinLocalDateTime(),
                    createdAt = it.createdAt.toKotlinLocalDateTime(),
                    updatedAt = it.updatedAt.toKotlinLocalDateTime()
                )
            }.toList()
    }
}
