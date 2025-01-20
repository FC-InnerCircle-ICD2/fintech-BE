package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.repository.PaymentRepository
import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : PaymentPort {
    override fun save(request: PaymentPort.Request): PaymentEntity {
        return paymentRepository.save(
            PaymentEntity(
                request.paymentKey,
                request.currency,
                request.userId,
                request.merchantId,
                request.paymentType,
                request.orderId,
                request.orderName
            )
        )
    }
}
