package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.domain.Payment
import com.inner.circle.core.structure.usecase.RequestPaymentUseCase
import com.inner.circle.infra.structure.port.SavePaymentPort
import org.springframework.stereotype.Service

internal class PaymentService(
    private val savePaymentPort: SavePaymentPort
) : RequestPaymentUseCase {
    override fun payment(request: RequestPaymentUseCase.Request) {
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
