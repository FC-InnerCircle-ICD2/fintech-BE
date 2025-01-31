package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.SavePaymentRequestPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Component

@Component
internal class SavePaymentRequestAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository
) : SavePaymentRequestPort {
    override fun save(request: SavePaymentRequestPort.Request) {
        paymentRequestRepository.save(
            PaymentRequestEntity(
                orderId = request.orderId,
                orderName = request.orderName,
                orderStatus = request.orderStatus,
                userId = request.userId,
                merchantId = request.merchantId,
                paymentKey = request.paymentKey,
                amount = request.amount,
                cardNumber = request.cardNumber,
                paymentType = request.paymentType,
                requestTime = request.requestTime
            )
        )
    }
}
