package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.Currency
import com.inner.circle.corebackoffice.domain.Payment
import com.inner.circle.corebackoffice.domain.PaymentTransaction
import com.inner.circle.corebackoffice.domain.PaymentType
import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.PaymentDto
import com.inner.circle.corebackoffice.service.dto.TransactionDto
import com.inner.circle.corebackoffice.usecase.ResponsePaymentUseCase
import com.inner.circle.infrabackoffice.port.FindPaymentByPaymentKeyPort
import com.inner.circle.infrabackoffice.port.FindTransactionByPaymentIdPort
import org.springframework.stereotype.Service

@Service
internal class PaymentService(
    private val findPaymentByPaymentKeyPort: FindPaymentByPaymentKeyPort,
    private val findTransactionByPaymentIdPort: FindTransactionByPaymentIdPort
) : ResponsePaymentUseCase {
    override fun getPayment(request: ResponsePaymentUseCase.Request): PaymentDto {
        val payment =
            findPaymentByPaymentKeyPort
                .findByPaymentKey(request.paymentKey)
                .map { payment ->
                    Payment(
                        payment.id,
                        payment.paymentKey,
                        Currency.of(payment.currency),
                        payment.userId,
                        payment.merchantId,
                        PaymentType.of(payment.paymentType),
                        payment.createdAt,
                        payment.updatedAt
                    )
                }.orElseThrow { throw IllegalArgumentException("Payment not found") }

        return PaymentDto.of(payment)
    }

    override fun getTransactions(request: ResponsePaymentUseCase.Request): List<TransactionDto> {
        val payment =
            findPaymentByPaymentKeyPort
                .findByPaymentKey(request.paymentKey)
                .orElseThrow { throw IllegalArgumentException("Payment not found") }
        val transactions =
            findTransactionByPaymentIdPort
                .findByPaymentId(payment.id)
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
