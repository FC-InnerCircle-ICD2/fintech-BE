package com.inner.circle.core.service

import com.inner.circle.core.domain.PaymentType
import com.inner.circle.core.domain.TransactionStatus
import com.inner.circle.core.service.dto.PaymentWithTransactionsDto
import com.inner.circle.core.service.dto.TransactionDto
import com.inner.circle.core.usecase.GetPaymentWithTransactionsUseCase
import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.port.GetPaymentPort
import com.inner.circle.infra.port.GetTransactionPort
import java.time.format.DateTimeFormatter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val getPaymentPort: GetPaymentPort,
    private val getTransactionPort: GetTransactionPort
) : GetPaymentWithTransactionsUseCase {
    override fun findAllByAccountId(
        request: GetPaymentWithTransactionsUseCase.FindAllByAccountIdRequest
    ): List<PaymentWithTransactionsDto> {
        validateDate(request.startDate, request.endDate)
        val payments =
            getPaymentPort
                .findAllByAccountId(
                    GetPaymentPort.FindAllByAccountIdRequest(
                        accountId = request.accountId,
                        startDate = request.startDate,
                        endDate = request.endDate,
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
                .mapValues { entry ->
                    entry.value.sortedByDescending { it.createdAt }
                }.let {
                    it.takeIf { request.status == null } ?: it.filterValues { transactions ->
                        transactions.any { transaction -> transaction.status == request.status }
                    }
                }

        return payments.mapNotNull { payment ->
            transactionMap[payment.paymentKey]?.let { transactions ->
                PaymentWithTransactionsDto(
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

    private fun validateDate(
        startDate: LocalDate?,
        endDate: LocalDate?
    ) {
        startDate?.let { start ->
            endDate?.let { end ->
                val locale = LocaleContextHolder.getLocale()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", locale)
                val currentDate =
                    java.time.LocalDate
                        .now()
                        .format(formatter)
                        .let { java.time.LocalDate.parse(it, formatter) }
                require(start <= end) {
                    throw PaymentException.InvalidParameterRequestException(
                        parameterName = null,
                        message = "endDate는 startDate 보다 빠를 수 없습니다."
                    )
                }
                require(
                    end.toJavaLocalDate() <= currentDate
                ) {
                    throw PaymentException.InvalidParameterRequestException(
                        parameterName = null,
                        message = "endDate는 현재 날짜보다 미래일 수 없습니다."
                    )
                }
            }
        }
    }
}
