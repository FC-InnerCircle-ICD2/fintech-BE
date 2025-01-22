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
                request.orderId,
                request.orderName,
                request.orderStatus,
                request.userId,
                request.merchantId,
                request.paymentKey,
                request.amount,
                request.requestTime
            )
        )
    }
}
