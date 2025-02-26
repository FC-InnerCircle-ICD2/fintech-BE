package com.inner.circle.core.service

import com.inner.circle.core.domain.Currency
import com.inner.circle.core.domain.PaymentType
import com.inner.circle.core.service.dto.PaymentApproveDto
import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import com.inner.circle.exception.CardCompanyException
import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.port.PaymentRequestPort
import com.inner.circle.infra.port.SavePaymentRequestPort
import com.inner.circle.infra.port.SaveTransactionPort
import com.inner.circle.infra.repository.entity.TransactionStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
internal class SavePaymentApproveService(
    private val paymentPort: PaymentPort,
    private val paymentRequestPort: PaymentRequestPort,
    private val saveTransactionPort: SaveTransactionPort,
    private val savePaymentRequestPort: SavePaymentRequestPort,
    private val httpClient: HttpClient,
    @Value("\${card.url.base-url}") private var baseUrl: String,
    @Value("\${card.url.approve-end-point}") private var endPoint: String
) : SavePaymentApproveUseCase {
    override fun saveApprove(request: SavePaymentApproveUseCase.Request): PaymentApproveDto {
        paymentRequestPort
            .findByPaymentKeyAndOrderId(
                PaymentRequestPort.Request(
                    paymentKey = request.paymentKey,
                    orderId = request.orderId
                )
            ).let { paymentRequest ->
                val paymentRequestDto =
                    PaymentRequestDto(
                        orderId = paymentRequest.orderId,
                        orderName = paymentRequest.orderName,
                        cardNumber = paymentRequest.cardNumber ?: "",
                        orderStatus = PaymentProcessStatus.valueOf(paymentRequest.orderStatus.name),
                        accountId = paymentRequest.accountId,
                        merchantId = paymentRequest.merchantId,
                        paymentKey =
                            paymentRequest.paymentKey
                                ?: throw PaymentException.PaymentKeyNotFoundException(),
                        amount = paymentRequest.amount,
                        paymentType = PaymentType.of(paymentRequest.paymentType),
                        requestTime = paymentRequest.requestTime
                    )

                if (request.amount.compareTo(paymentRequest.amount) != 0) {
                    throw PaymentException.InvalidAmountException(
                        request.paymentKey
                    )
                }

                if (paymentRequestDto.orderStatus != PaymentProcessStatus.IN_PROGRESS) {
                    throw PaymentException.InvalidOrderStatusException(
                        paymentRequestDto.orderStatus.toString()
                    )
                }

                val cardApproveMap: Map<String, Any> =
                    httpClient.sendPostRequest(
                        baseUrl,
                        endPoint,
                        mapOf(
                            "cardNumber" to paymentRequest.cardNumber!!,
                            "amount" to paymentRequest.amount
                        )
                    )

                if (cardApproveMap["isValid"] as Boolean) {
                    paymentPort
                        .save(
                            PaymentPort.Request(
                                id = null,
                                paymentKey =
                                    paymentRequest.paymentKey
                                        ?: throw PaymentException.PaymentKeyNotFoundException(),
                                cardNumber =
                                    paymentRequest.cardNumber
                                        ?: throw PaymentException.CardNotFoundException(),
                                currency = Currency.toInfraCurrency(Currency.KRW),
                                accountId = paymentRequest.accountId,
                                merchantId = paymentRequest.merchantId,
                                paymentType = paymentRequest.paymentType,
                                orderId = paymentRequest.orderId,
                                orderName = paymentRequest.orderName
                            )
                        ).let {
                            saveTransactionPort.save(
                                SaveTransactionPort.Request(
                                    id = null,
                                    paymentKey =
                                        paymentRequest.paymentKey
                                            ?: throw PaymentException.PaymentKeyNotFoundException(),
                                    amount = paymentRequestDto.amount,
                                    status = TransactionStatus.APPROVED,
                                    reason = null,
                                    requestedAt = paymentRequest.requestTime
                                )
                            )

                            savePaymentRequestPort.save(
                                SavePaymentRequestPort.Request(
                                    orderId = paymentRequest.orderId,
                                    orderName = paymentRequest.orderName,
                                    orderStatus = PaymentProcessStatus.DONE,
                                    accountId = paymentRequest.accountId,
                                    merchantId = paymentRequest.merchantId,
                                    merchantName = paymentRequest.merchantName,
                                    paymentKey =
                                        paymentRequest.paymentKey
                                            ?: throw PaymentException.PaymentKeyNotFoundException(),
                                    amount = paymentRequest.amount,
                                    cardNumber = paymentRequest.cardNumber,
                                    paymentType = paymentRequest.paymentType,
                                    requestTime = paymentRequest.requestTime
                                )
                            )
                        }

                    return PaymentApproveDto(
                        orderId = paymentRequest.orderId,
                        paymentKey =
                            paymentRequest.paymentKey
                                ?: throw PaymentException.PaymentKeyNotFoundException(),
                        amount = paymentRequest.amount
                    )
                } else {
                    throw CardCompanyException.CardNotApproveException(
                        paymentRequest.cardNumber!!
                    )
                }
            }
    }
}
