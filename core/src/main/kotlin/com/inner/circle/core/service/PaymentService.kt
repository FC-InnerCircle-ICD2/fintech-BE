package com.inner.circle.core.service

import com.inner.circle.core.domain.Payment
import com.inner.circle.infra.port.SavePaymentPort
import org.springframework.stereotype.Service

@Service
internal class PaymentService(
    private val savePaymentPort: SavePaymentPort
) : com.inner.circle.core.usecase.RequestPaymentUseCase {
    override fun payment(request: com.inner.circle.core.usecase.RequestPaymentUseCase.Request) {
        val payment = Payment(request.userName, request.amount)
        savePaymentPort.save(
            SavePaymentPort.Request(
                payment.userName,
                payment.amount,
                payment.requestAt
            )
        )
    }
}
