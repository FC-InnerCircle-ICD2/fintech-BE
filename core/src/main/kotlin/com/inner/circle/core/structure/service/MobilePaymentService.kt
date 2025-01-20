package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto
import com.inner.circle.core.structure.usecase.MobilePaymentUseCase
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
    private val savePaymentRequestPort: SavePaymentRequestPort
) : MobilePaymentUseCase {
    override fun confirmPayment(request: MobilePaymentUseCase.Request): MobilePaymentCoreDto {
        val paymentInfo =
            mobilePaymentPort.getCardNoAndPayInfo(
                MobilePaymentPort.Request(orderId = request.orderId)
            )

        val cardNumber: String = request.cardNumber ?: paymentInfo.cardNumber

        val paymentAuth =
            cardPaymentAuthPort.doPaymentAuth(
                CardPaymentAuthPort.Request(cardNumber = cardNumber)
            )

        val result: MobilePaymentCoreDto

        if (paymentAuth.isValid) {
            val paymentKeyUUID = UUID.randomUUID().toString()
            result =
                MobilePaymentCoreDto(
                    paymentKey = paymentKeyUUID,
                    orderId = paymentInfo.orderId,
                    cardNumber = paymentInfo.cardNumber,
                    amount = paymentInfo.amount,
                    errorCode = null,
                    errorMessage = null,
                    successYn = true
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
        } else {
            val paymentException =
                AuthenticateException.CardAuthFailException()
            result =
                MobilePaymentCoreDto(
                    paymentKey = null,
                    orderId = paymentInfo.orderId,
                    cardNumber = paymentInfo.cardNumber,
                    amount = paymentInfo.amount,
                    errorCode = paymentException.status.toString(),
                    errorMessage = paymentException.message,
                    successYn = false
                )
        }

        return result
    }
}
