package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto
import com.inner.circle.core.structure.service.dto.PaymentInfoDto
import com.inner.circle.core.structure.sse.SseConnectionPool
import com.inner.circle.core.structure.usecase.MobilePaymentUseCase
import com.inner.circle.core.structure.usecase.SimpleMobilePaymentUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.infra.structure.port.CardPaymentAuthPort
import com.inner.circle.infra.structure.port.MobilePaymentPort
import com.inner.circle.infra.structure.port.SavePaymentRequestPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
internal class MobilePaymentService(
    private val mobilePaymentPort: MobilePaymentPort,
    private val cardPaymentAuthPort: CardPaymentAuthPort,
    private val savePaymentRequestPort: SavePaymentRequestPort,
    private val sseConnectionPool: SseConnectionPool
) : MobilePaymentUseCase {
    private fun authPayment(request: PaymentInfoDto): MobilePaymentCoreDto {
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
            MobilePaymentCoreDto(
                paymentKey = paymentKeyUUID,
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
        val orderConnection = sseConnectionPool.getSession(request.orderId)
        orderConnection.sendMessage(result)

        return result
    }

    override fun confirmPayment(request: SimpleMobilePaymentUseCase.Request): MobilePaymentCoreDto {
        val paymentInfo =
            mobilePaymentPort.getCardNoAndPayInfo(
                MobilePaymentPort.Request(orderId = request.orderId)
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

    override fun confirmPayment(request: MobilePaymentUseCase.Request): MobilePaymentCoreDto {
        val paymentInfo =
            mobilePaymentPort.getCardNoAndPayInfo(
                MobilePaymentPort.Request(orderId = request.orderId)
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
