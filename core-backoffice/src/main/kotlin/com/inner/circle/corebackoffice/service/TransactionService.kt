package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.PaymentType
import com.inner.circle.corebackoffice.domain.TransactionStatus
import com.inner.circle.corebackoffice.domain.convertInfraTransactionStatus
import com.inner.circle.corebackoffice.service.dto.PaymentWithTransactionsDto
import com.inner.circle.corebackoffice.service.dto.TransactionDto
import com.inner.circle.corebackoffice.usecase.CancelPaymentUseCase
import com.inner.circle.corebackoffice.usecase.GetPaymentWithTransactionsUseCase
import com.inner.circle.exception.BackofficeException
import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import com.inner.circle.infrabackoffice.port.GetTransactionPort
import com.inner.circle.infrabackoffice.port.SaveTransactionPort
import java.time.ZoneId
import java.util.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
internal class TransactionService(
    private val getPaymentPort: GetPaymentPort,
    private val getTransactionPort: GetTransactionPort,
    private val saveTransactionPort: SaveTransactionPort
) : GetPaymentWithTransactionsUseCase,
    CancelPaymentUseCase {
    override fun findAllByMerchantId(
        request: GetPaymentWithTransactionsUseCase.FindAllByMerchantIdRequest
    ): List<PaymentWithTransactionsDto> {
        validateDate(request.startDate, request.endDate)
        val payments =
            getPaymentPort
                .findAllByMerchantId(
                    GetPaymentPort.FindAllByMerchantIdRequest(
                        merchantId = request.merchantId,
                        paymentKey = request.paymentKey,
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
                .let {
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

    override fun cancel(request: CancelPaymentUseCase.CancelPaymentRequest): TransactionDto {
        val payment =
            getPaymentPort.findByMerchantIdAndPaymentKey(
                GetPaymentPort.FindByPaymentKeyRequest(
                    merchantId = request.merchantId,
                    paymentKey = request.paymentKey
                )
            )

        val transactions =
            getTransactionPort.findAllByPaymentKey(
                GetTransactionPort.Request(
                    paymentKey = payment.paymentKey
                )
            )
        require(transactions.isNotEmpty()) {
            throw PaymentException.PaymentKeyNotFoundException(request.paymentKey)
        }

        val cancelableAmount = transactions.sumOf { it.amount }
        require(request.amount <= cancelableAmount) {
            throw BackofficeException.ExceedCancelAmountException(
                paymentKey = request.paymentKey,
                amount = cancelableAmount
            )
        }

        val transaction =
            saveTransactionPort.save(
                SaveTransactionPort.Request(
                    id = null,
                    paymentKey = request.paymentKey,
                    amount = -request.amount,
                    status = TransactionStatus.CANCELED.convertInfraTransactionStatus(),
                    reason = null,
                    requestedAt = transactions.first().requestedAt
                )
            )

        return TransactionDto.of(transaction)
    }

    private fun validateDate(
        startDate: LocalDate?,
        endDate: LocalDate?
    ) {
        startDate?.let { start ->
            endDate?.let { end ->
                val locale = LocaleContextHolder.getLocale()
                val zoneId = getZoneIdForLocale(locale)
                val currentDate = java.time.LocalDate.now(zoneId)
                require(start <= end) {
                    throw BackofficeException.InvalidParameterRequestException(
                        parameterName = null,
                        message = "endDate는 startDate 보다 빠를 수 없습니다."
                    )
                }
                require(
                    end.toJavaLocalDate() <= currentDate
                ) {
                    throw BackofficeException.InvalidParameterRequestException(
                        parameterName = null,
                        message = "endDate($end)는 현재($currentDate) 보다 미래일 수 없습니다."
                    )
                }
            }
        }
    }

    fun getZoneIdForLocale(locale: Locale): ZoneId =
        when (locale.country) {
            "KR" -> ZoneId.of("Asia/Seoul")
            "US" -> ZoneId.of("America/New_York")
            "JP" -> ZoneId.of("Asia/Tokyo")
            "CN" -> ZoneId.of("Asia/Shanghai")
            "DE" -> ZoneId.of("Europe/Berlin")
            else -> ZoneId.of("UTC")
        }
}
