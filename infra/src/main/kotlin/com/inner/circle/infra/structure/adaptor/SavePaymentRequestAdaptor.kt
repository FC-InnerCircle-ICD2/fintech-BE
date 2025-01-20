package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.port.SavePaymentRequestPort
import com.inner.circle.infra.structure.repository.TestPaymentRequestRepository
import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Component

internal class SavePaymentRequestAdaptor(
    private val paymentRequestRepository: TestPaymentRequestRepository
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
