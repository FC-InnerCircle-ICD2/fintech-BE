package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.PaymentType
import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.service.dto.PaymentWithTransactionsDto
import com.inner.circle.corebackoffice.service.dto.TransactionDto
import com.inner.circle.corebackoffice.usecase.GetPaymentWithTransactionsUseCase
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val getPaymentPort: GetPaymentPort,
    private val getTransactionPort: GetTransactionPort
) : GetPaymentWithTransactionsUseCase {
    override fun findAllByMerchantId(
        request: GetPaymentWithTransactionsUseCase.FindAllByMerchantIdRequest
    ): List<PaymentWithTransactionsDto> {
        val payments =
            getPaymentPort
                .findAllByMerchantId(
                    GetPaymentPort.FindAllByMerchantIdRequest(
                        merchantId = request.merchantId,
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
            getPaymentPort.findByMerchantIdAndPaymentKey(
                GetPaymentPort.FindByPaymentKeyRequest(
                    merchantId = request.merchantId,
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
