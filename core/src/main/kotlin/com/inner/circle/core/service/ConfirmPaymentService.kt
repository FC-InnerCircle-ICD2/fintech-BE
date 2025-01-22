package com.inner.circle.core.service

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import com.inner.circle.core.service.dto.PaymentInfoDto
import com.inner.circle.core.sse.SseConnectionPool
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.infra.port.CardPaymentAuthPort
import com.inner.circle.infra.port.ConfirmPaymentPort
import com.inner.circle.infra.port.SavePaymentRequestPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
internal class ConfirmPaymentService(
    private val confirmPaymentPort: ConfirmPaymentPort,
    private val cardPaymentAuthPort: CardPaymentAuthPort,
    private val savePaymentRequestPort: SavePaymentRequestPort,
    private val sseConnectionPool: SseConnectionPool
) : ConfirmPaymentUseCase {
    private fun authPayment(request: PaymentInfoDto): ConfirmPaymentCoreDto {
        val paymentAuth =
            cardPaymentAuthPort.doPaymentAuth(
                CardPaymentAuthPort.Request(cardNumber = request.cardNumber)
            )

        // 어댑터로 이동 예정
        if (!paymentAuth.isValid) {
            throw AuthenticateException.CardAuthFailException()
        }

        val paymentKeyUUID = UUID.randomUUID().toString()

        val result =
            ConfirmPaymentCoreDto(
                paymentKey = paymentKeyUUID,
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
                userId = request.userId,
                merchantId = request.merchantId,
                paymentKey = paymentKeyUUID,
                amount = request.amount,
                requestTime = request.requestTime
            )
        )

        // sse 전송
        val orderConnection = sseConnectionPool.getSession(
            request.merchantId+"_"+request.orderId
        )
        orderConnection.sendMessage(result)

        return result
    }

    override fun confirmPayment(request: ConfirmSimplePaymentUseCase.Request): ConfirmPaymentCoreDto {
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
                userId = paymentInfo.userId,
                merchantId = paymentInfo.merchantId,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber = paymentInfo.cardNumber,
                expirationPeriod = paymentInfo.expirationPeriod,
                cvc = paymentInfo.cvc
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
                userId = paymentInfo.userId,
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
