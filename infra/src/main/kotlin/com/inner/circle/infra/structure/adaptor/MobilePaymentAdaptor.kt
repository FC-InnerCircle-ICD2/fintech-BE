package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.adaptor.dto.MobilePaymentInfraDto
import com.inner.circle.infra.structure.port.MobilePaymentPort
import com.inner.circle.infra.structure.repository.PaymentRequestRepository
import com.inner.circle.infra.structure.repository.UserCardRepository
import org.springframework.stereotype.Component

@Component
internal class MobilePaymentAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository,
    private val userCardRepository: UserCardRepository
) : MobilePaymentPort {
    override fun getCardNoAndPayInfo(request: MobilePaymentPort.Request): MobilePaymentInfraDto {
        val paymentRequest =
            paymentRequestRepository.findByOrderId(request.orderId)
                ?: throw IllegalArgumentException(
                    "Payment not found - order_id: ${request.orderId}"
                )
        return userCardRepository
            .findByUserId(paymentRequest.userId)
            ?.let {
                MobilePaymentInfraDto(
                    paymentRequest.orderId,
                    paymentRequest.orderName,
                    paymentRequest.orderStatus,
                    paymentRequest.userId,
                    paymentRequest.merchantId,
                    paymentRequest.paymentKey,
                    paymentRequest.amount,
                    paymentRequest.requestTime,
                    it.cardNumber
                )
            } ?: throw IllegalArgumentException(
            "Payment not found - order_id: ${paymentRequest.orderId}"
        )
    }
}
