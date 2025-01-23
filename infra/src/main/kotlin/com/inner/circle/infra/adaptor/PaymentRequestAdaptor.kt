package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.port.PaymentRequestPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentRequestAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository
) : PaymentRequestPort {
    override fun findByPaymentKeyAndOrderId(
        request: PaymentRequestPort.Request
    ): PaymentRequestEntity =
        paymentRequestRepository.findByPaymentKeyAndOrderId(request.paymentKey, request.orderId)
            ?: throw PaymentException.PaymentRequestNotFoundException(
                paymentKey = request.paymentKey
            )
}
