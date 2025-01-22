package com.inner.circle.infra.adaptor

import com.inner.circle.infra.adaptor.dto.MobilePaymentInfraDto
import com.inner.circle.infra.port.MobilePaymentPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.UserCardRepository
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
                    orderId = paymentRequest.orderId,
                    orderName = paymentRequest.orderName,
                    orderStatus = paymentRequest.orderStatus,
                    userId = paymentRequest.userId,
                    merchantId = paymentRequest.merchantId,
                    paymentKey = paymentRequest.paymentKey,
                    amount = paymentRequest.amount,
                    requestTime = paymentRequest.requestTime,
                    cardNumber = it.cardNumber
                )
            } ?: throw IllegalArgumentException(
            "Payment not found - order_id: ${paymentRequest.orderId}"
        )
    }
}
