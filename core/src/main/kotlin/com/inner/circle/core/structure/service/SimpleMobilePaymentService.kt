package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto
import com.inner.circle.core.structure.usecase.SimpleMobilePaymentUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.infra.structure.port.CardPaymentAuthPort
import com.inner.circle.infra.structure.port.MobilePaymentPort
import com.inner.circle.infra.structure.port.SavePaymentRequestPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
internal class SimpleMobilePaymentService(
    private val mobilePaymentPort: MobilePaymentPort,
    private val cardPaymentAuthPort: CardPaymentAuthPort,
    private val savePaymentRequestPort: SavePaymentRequestPort
) : SimpleMobilePaymentUseCase {
    override fun confirmPayment(request: SimpleMobilePaymentUseCase.Request): MobilePaymentCoreDto {
        val paymentInfo =
            mobilePaymentPort.getCardNoAndPayInfo(
                MobilePaymentPort.Request(orderId = request.orderId)
            )

        val paymentAuth =
            cardPaymentAuthPort.doPaymentAuth(
                CardPaymentAuthPort.Request(cardNumber = paymentInfo.cardNumber)
            )

        if (!paymentAuth.isValid) {
            throw AuthenticateException.CardAuthFailException()
        }

        val paymentKeyUUID = UUID.randomUUID().toString()

        val result =
            MobilePaymentCoreDto(
                paymentKey = paymentKeyUUID,
                orderId = paymentInfo.orderId,
                cardNumber = paymentInfo.cardNumber,
                amount = paymentInfo.amount
            )

        // PaymentRequest에 대한 서비스 생성 후 분리 예정
        savePaymentRequestPort.save(
            SavePaymentRequestPort.Request(
                orderId = paymentInfo.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                userId = paymentInfo.userId,
                merchantId = paymentInfo.merchantId,
                paymentKey = paymentKeyUUID,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime
            )
        )

        return result
    }
}
