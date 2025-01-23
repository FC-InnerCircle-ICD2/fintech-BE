package com.inner.circle.core.service

import com.inner.circle.core.service.dto.PaymentApproveDto
import com.inner.circle.core.service.dto.PaymentDto
import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import com.inner.circle.exception.CardCompanyException
import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.port.PaymentRequestPort
import com.inner.circle.infra.port.TransactionPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
internal class SavePaymentApproveService(
    private val paymentPort: PaymentPort,
    private val paymentRequestPort: PaymentRequestPort,
    private val transactionPort: TransactionPort,
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
                        cardNumber = paymentRequest.cardNumber,
                        orderStatus = paymentRequest.orderStatus,
                        userId = paymentRequest.userId,
                        merchantId = paymentRequest.merchantId,
                        paymentKey = paymentRequest.paymentKey,
                        amount = paymentRequest.amount,
                        paymentType = paymentRequest.paymentType,
                        requestTime = paymentRequest.requestTime
                    )

                if (request.amount.equals(paymentRequest.amount)) {
                    throw PaymentException.InvalidAmountException(
                        request.paymentKey
                    )
                }

                val cardApproveMap: Map<String, Any> =
                    HttpClient.sendPostRequest(
                        baseUrl,
                        endPoint,
                        mapOf(
                            "cardNumber" to paymentRequest.cardNumber,
                            "amount" to paymentRequest.amount
                        )
                    )

                if (cardApproveMap["isValid"] as Boolean) {
                    paymentPort
                        .save(
                            PaymentPort.Request(
                                paymentRequest.paymentKey,
                                "KSW",
                                paymentRequest.userId,
                                paymentRequest.merchantId,
                                paymentRequest.paymentType,
                                paymentRequest.orderId,
                                paymentRequest.orderName
                            )
                        ).let { payment ->
                            val paymentDto =
                                PaymentDto(
                                    paymentKey = payment.paymentKey,
                                    currency = payment.currency,
                                    userId = payment.userId,
                                    merchantId = payment.merchantId,
                                    paymentType = payment.paymentType,
                                    orderId = payment.orderId
                                )

                            transactionPort.save(
                                TransactionPort.Request(
                                    paymentKey = paymentDto.paymentKey,
                                    amount = paymentRequestDto.amount,
                                    status = "APPROVE",
                                    reason = null
                                )
                            )
                        }

                    return PaymentApproveDto(
                        orderId = paymentRequest.orderId,
                        paymentKey = paymentRequest.paymentKey,
                        amount = paymentRequest.amount
                    )
                } else {
                    throw CardCompanyException.CardNotApproveException(
                        paymentRequest.cardNumber
                    )
                }
            }
    }
}
