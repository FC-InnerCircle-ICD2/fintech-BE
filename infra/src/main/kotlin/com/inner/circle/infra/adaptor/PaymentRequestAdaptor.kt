package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.PaymentRequestPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentRequestAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository
) : PaymentRequestPort {
    override fun findByPaymentKey(
        request: PaymentRequestPort.Request
    ): PaymentRequestEntity {
        return paymentRequestRepository.findByPaymentKeyAndOrderId(request.paymentKey, request.orderId)?: throw IllegalArgumentException(
            "Payment Request not found - payment_key: ${request.paymentKey}, order_id: ${request.orderId}"
        )
    }
}
