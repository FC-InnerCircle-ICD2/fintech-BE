package com.inner.circle.core.service

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import com.inner.circle.core.service.dto.PaymentInfoDto
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.ConfirmPaymentPort
import com.inner.circle.infra.port.SavePaymentRequestPort
import com.inner.circle.infra.repository.entity.PaymentType
import io.hypersistence.tsid.TSID
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
internal class ConfirmPaymentService(
    private val confirmPaymentPort: ConfirmPaymentPort,
    private val savePaymentRequestPort: SavePaymentRequestPort,
    private val httpClient: HttpClient,
    @Value("\${card.url.base-url}") private var baseUrl: String,
    @Value("\${card.url.validate-end-point}") private var endPoint: String
) : ConfirmPaymentUseCase {
    private val logger: Logger = LoggerFactory.getLogger(ConfirmPaymentService::class.java)

    private fun authPayment(request: PaymentInfoDto): ConfirmPaymentCoreDto {
        val cardValidateMap: Map<String, Any> =
            httpClient.sendPostRequest(
                baseUrl,
                endPoint,
                mapOf(
                    "cardNumber" to request.cardNumber,
                    "expiryDate" to request.expirationPeriod,
                    "cvc" to request.cvc,
                    "cardCompany" to request.cardCompany
                )
            )

        // 어댑터로 이동 예정
        if (!(cardValidateMap["isValid"] as Boolean)) {
            throw AuthenticateException.CardAuthFailException(request.cardNumber)
        }

        if (request.orderStatus != PaymentProcessStatus.READY) {
            throw PaymentException.InvalidOrderStatusException(
                request.orderStatus.toString()
            )
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
                orderStatus = PaymentProcessStatus.IN_PROGRESS,
                accountId = request.accountId,
                merchantId = request.merchantId,
                merchantName = request.merchantName,
                paymentKey = paymentKeyTsid,
                amount = request.amount,
                cardNumber = request.cardNumber,
                paymentType = PaymentType.CARD,
                requestTime = request.requestTime
            )
        )
        return result
    }

    override fun confirmPayment(
        request: ConfirmSimplePaymentUseCase.Request
    ): ConfirmPaymentCoreDto {
        val paymentInfo =
            confirmPaymentPort.getCardNoAndPayInfo(
                ConfirmPaymentPort.Request(
                    orderId = request.orderId,
                    merchantId = request.merchantId,
                    accountId = request.accountId
                )
            )

        if (paymentInfo.cardNumber.isNullOrBlank() ||
            paymentInfo.cvc.isNullOrBlank()
        ) {
            throw PaymentException.CardNotFoundException()
        }

        return authPayment(
            PaymentInfoDto(
                orderId = request.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                merchantName = paymentInfo.merchantName,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber =
                    paymentInfo.cardNumber
                        ?: throw AuthenticateException.CardInformationNotFoundException(),
                expirationPeriod =
                    paymentInfo.expirationPeriod
                        ?: throw AuthenticateException.CardInformationNotFoundException(),
                cvc =
                    paymentInfo.cvc
                        ?: throw AuthenticateException.CardInformationNotFoundException(),
                cardCompany =
                    paymentInfo.cardCompany
                        ?: throw AuthenticateException.CardInformationNotFoundException()
            )
        )
    }

    override fun confirmPayment(request: ConfirmPaymentUseCase.Request): ConfirmPaymentCoreDto {
        val paymentInfo =
            confirmPaymentPort.getCardNoAndPayInfo(
                ConfirmPaymentPort.Request(
                    orderId = request.orderId,
                    merchantId = request.merchantId,
                    accountId = null
                )
            )

        return authPayment(
            PaymentInfoDto(
                orderId = request.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                merchantName = paymentInfo.merchantName,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber = request.cardNumber,
                expirationPeriod = request.expirationPeriod,
                cvc = request.cvc,
                cardCompany = request.cardCompany
            )
        )
    }
}
