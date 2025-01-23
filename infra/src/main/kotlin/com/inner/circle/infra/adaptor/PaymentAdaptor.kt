package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.repository.PaymentRepository
import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : PaymentPort {
    override fun save(request: PaymentPort.Request): PaymentEntity =
        paymentRepository.save(
            PaymentEntity(
                paymentKey = request.paymentKey,
                currency = request.currency,
                userId = request.userId,
                merchantId = request.merchantId,
                paymentType = request.paymentType,
                orderId = request.orderId,
                orderName = request.orderName
            )
        ) ?: throw PaymentException.PaymentNotSaveException(
            paymentKey = request.paymentKey
        )
}
