package com.inner.circle.core.service

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import com.inner.circle.core.service.dto.PaymentInfoDto
import com.inner.circle.core.sse.SseConnectionPool
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.ConfirmPaymentPort
import com.inner.circle.infra.port.SavePaymentRequestPort
import io.hypersistence.tsid.TSID
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private const val REQUIRED_TSID_LENGTH = 13

@Service
internal class ConfirmPaymentService(
    private val confirmPaymentPort: ConfirmPaymentPort,
    private val savePaymentRequestPort: SavePaymentRequestPort,
    private val sseConnectionPool: SseConnectionPool,
    @Value("\${card.url.base-url}") private var baseUrl: String,
    @Value("\${card.url.validate-end-point}") private var endPoint: String
) : ConfirmPaymentUseCase {
    private val logger: Logger = LoggerFactory.getLogger(ConfirmPaymentService::class.java)

    private fun authPayment(request: PaymentInfoDto): ConfirmPaymentCoreDto {
        val cardValidateMap: Map<String, Any> =
            HttpClient.sendPostRequest(
                baseUrl,
                endPoint,
                mapOf(
                    "cardNumber" to request.cardNumber,
                    "expiryDate" to request.expirationPeriod,
                    "cvc" to request.cvc
                )
            )

        // 어댑터로 이동 예정
        if (!(cardValidateMap["isValid"] as Boolean)) {
            throw AuthenticateException.CardAuthFailException()
        }

        val paymentKeyTsid = TSID.fast().toString()

        val result =
            ConfirmPaymentCoreDto(
                paymentKey = paymentKeyTsid,
                merchantId = request.merchantId,
                orderId = request.orderId,
                cardNumber = request.cardNumber,
                amount = request.amount
            )

        // PaymentRequest에 대한 서비스 생성 후 분리 예정
        savePaymentRequestPort.save(
            SavePaymentRequestPort.Request(
                orderId = request.orderId,
                orderName = request.orderName,
                orderStatus = request.orderStatus,
                accountId = request.accountId,
                merchantId = request.merchantId,
                paymentKey = paymentKeyTsid,
                amount = request.amount,
                cardNumber = request.cardNumber,
                paymentType = "CARD",
                requestTime = request.requestTime
            )
        )

        // sse 전송 일단 try catch로 구현 이후 Controller에 통합 후 삭제 예정
        try {
            val orderConnection =
                sseConnectionPool.getSession(
                    request.merchantId + "_" + request.orderId
                )
            orderConnection.sendMessage(result)
        } catch (e: Exception) {
            logger.error("SSE connection not found", e)
        }

        return result
    }

    override fun confirmPayment(
        request: ConfirmSimplePaymentUseCase.Request
    ): ConfirmPaymentCoreDto {
        val paymentInfo =
            confirmPaymentPort.getCardNoAndPayInfo(
                ConfirmPaymentPort.Request(
                    orderId = request.orderId,
                    merchantId = request.merchantId
                )
            )

        return authPayment(
            PaymentInfoDto(
                orderId = request.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber =
                    paymentInfo.cardNumber ?: throw AuthenticateException.CardNotFoundException(),
                expirationPeriod =
                    paymentInfo.expirationPeriod
                        ?: throw AuthenticateException.CardNotFoundException(),
                cvc = paymentInfo.cvc ?: throw AuthenticateException.CardNotFoundException()
            )
        )
    }

    override fun confirmPayment(request: ConfirmPaymentUseCase.Request): ConfirmPaymentCoreDto {
        val paymentInfo =
            confirmPaymentPort.getCardNoAndPayInfo(
                ConfirmPaymentPort.Request(
                    orderId = request.orderId,
                    merchantId = request.merchantId
                )
            )

        return authPayment(
            PaymentInfoDto(
                orderId = request.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber = request.cardNumber,
                expirationPeriod = request.expirationPeriod,
                cvc = request.cvc
            )
        )
    }
}
