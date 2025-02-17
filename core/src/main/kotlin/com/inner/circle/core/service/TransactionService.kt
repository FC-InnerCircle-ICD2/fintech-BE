package com.inner.circle.core.service

import com.inner.circle.core.domain.PaymentType
import com.inner.circle.core.domain.TransactionStatus
import com.inner.circle.core.service.dto.PaymentWithTransactionsDto
import com.inner.circle.core.service.dto.TransactionDto
import com.inner.circle.core.usecase.GetPaymentWithTransactionsUseCase
import com.inner.circle.infra.port.GetPaymentPort
import com.inner.circle.infra.port.GetTransactionPort
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val getPaymentPort: GetPaymentPort,
    private val getTransactionPort: GetTransactionPort
) : GetPaymentWithTransactionsUseCase {
    override fun findAllByAccountId(
        request: GetPaymentWithTransactionsUseCase.FindAllByAccountIdRequest
    ): List<PaymentWithTransactionsDto> {
        val payments =
            getPaymentPort
                .findAllByAccountId(
                    GetPaymentPort.FindAllByAccountIdRequest(
                        accountId = request.accountId,
                        page = request.page,
                        limit = request.limit
                    )
                ).takeIf { it.isNotEmpty() } ?: return emptyList()

        val transactionMap =
            getTransactionPort
                .findAllByPaymentKeyIn(paymentKeys = payments.map { it.paymentKey })
                .map { transaction ->
                    TransactionDto(
                        id = transaction.id,
                        paymentKey = transaction.paymentKey,
                        amount = transaction.amount,
                        status = TransactionStatus.of(transaction.status),
                        reason = transaction.reason,
                        requestedAt = transaction.requestedAt,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt
                    )
                }.groupBy { it.paymentKey }

        return payments.map { payment ->
            PaymentWithTransactionsDto(
                paymentKey = payment.paymentKey,
                cardNumber = payment.cardNumber,
                accountId = payment.accountId,
                transactions = transactionMap[payment.paymentKey].orEmpty(),
                paymentType = PaymentType.of(payment.paymentType),
                orderId = payment.orderId,
                orderName = payment.orderName
            )
        }
    }

    override fun findByPaymentKey(
        request: GetPaymentWithTransactionsUseCase.FindByPaymentKeyRequest
    ): PaymentWithTransactionsDto {
        val payment =
            getPaymentPort.findByAccountIdAndPaymentKey(
                GetPaymentPort.FindByPaymentKeyRequest(
                    accountId = request.accountId,
                    paymentKey = request.paymentKey
                )
            )

        val transactions =
            getTransactionPort
                .findAllByPaymentKey(
                    GetTransactionPort.Request(
                        paymentKey = request.paymentKey
                    )
                ).map { transaction ->
                    TransactionDto(
                        id = transaction.id,
                        paymentKey = transaction.paymentKey,
                        amount = transaction.amount,
                        status = TransactionStatus.of(transaction.status),
                        reason = transaction.reason,
                        requestedAt = transaction.requestedAt,
                        createdAt = transaction.createdAt,
                        updatedAt = transaction.updatedAt
                    )
                }.toList()

        return PaymentWithTransactionsDto(
            paymentKey = payment.paymentKey,
            cardNumber = payment.cardNumber,
            accountId = payment.accountId,
            transactions = transactions,
            paymentType = PaymentType.of(payment.paymentType),
            orderId = payment.orderId,
            orderName = payment.orderName
        )
    }
}
