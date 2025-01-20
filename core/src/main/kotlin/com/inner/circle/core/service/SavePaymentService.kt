package com.inner.circle.core.service

import com.inner.circle.core.service.dto.PaymentDto
import com.inner.circle.core.usecase.SavePaymentUseCase
import com.inner.circle.infra.port.PaymentPort
import org.springframework.stereotype.Service

@Service
internal class SavePaymentService(
    private val paymentPort: PaymentPort
) : SavePaymentUseCase {
    override fun save(request: SavePaymentUseCase.Request): PaymentDto {
        return paymentPort.save(
            PaymentPort.Request(
                request.paymentKey,
                request.currency,
                request.userId,
                request.merchantId,
                request.paymentType,
                request.orderId,
                request.orderName
            )
        ).let {
            PaymentDto(
                paymentKey = it.paymentKey,
                currency = it.currency,
                userId = it.userId,
                merchantId = it.merchantId,
                paymentType = it.paymentType,
                orderId = it.orderId,
            )
        }
    }
}
