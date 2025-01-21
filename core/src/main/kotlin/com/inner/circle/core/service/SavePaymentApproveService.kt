package com.inner.circle.core.service

import com.inner.circle.core.service.dto.PaymentDto
import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.GetPaymentRequestUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import com.inner.circle.core.usecase.SavePaymentUseCase
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.port.PaymentRequestPort
import com.inner.circle.infra.port.TransactionPort
import java.math.BigDecimal
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
internal class SavePaymentApproveService(
    private val paymentPort: PaymentPort,
    private val paymentRequestPort: PaymentRequestPort,
    private val transactionPort: TransactionPort,
    @Value("\${card.url.base-url}") private var baseUrl: String,
    @Value("\${card.url.point}") private var endPoint: String
) : SavePaymentApproveUseCase {
    override fun saveApprove(request: SavePaymentApproveUseCase.Request): PaymentDto {
        paymentRequestPort.findByPaymentKey(
            PaymentRequestPort.Request(
                paymentKey = request.paymentKey,
                orderId = request.orderId
            )
        ).let {
            PaymentRequestDto(
                orderId = it.orderId,
                orderName = it.orderName,
                cardNumber = it.cardNumber,
                orderStatus = it.orderStatus,
                userId = it.userId,
                merchantId = it.merchantId,
                paymentKey = it.paymentKey,
                amount = it.amount,
                paymentType = it.paymentType,
                requestTime = it.requestTime,
            )
        }.let {
            val cardApproveMap: Map<String, Any> = HttpClient.sendPostRequest(baseUrl, endPoint, mapOf(
                "cardNumber" to it.cardNumber,
                "amount" to it.amount
            ))

            if(cardApproveMap["isValid"] as Boolean) {
                paymentPort.save(
                    PaymentPort.Request(
                        it.paymentKey,
                        "KSW",
                        it.userId,
                        it.merchantId,
                        it.paymentType,
                        it.orderId,
                        it.orderName
                    )
                ).let {
                    PaymentDto(
                        paymentKey = it.paymentKey,
                        currency = it.currency,
                        userId = it.userId,
                        merchantId = it.merchantId,
                        paymentType = it.paymentType,
                        orderId = it.orderId,
                    )
                }.let {
//                    transactionPort.save(
//                        TransactionPort.Request(
//                            it.paymentKey,
//                            val payment: PaymentEntity,
//                            it.am,
//                            val status: String,
//                            val reason: String?
//                        )
//                    )
                }
            } else {
                return PaymentDto(
                    paymentKey = it.paymentKey,
                    "KSW",
                    it.userId,
                    it.merchantId,
                    it.paymentType,
                    it.orderId,
                )
            }
            return PaymentDto(
                paymentKey = it.paymentKey,
                "KSW",
                it.userId,
                it.merchantId,
                it.paymentType,
                it.orderId,
            )
        }
    }
}
