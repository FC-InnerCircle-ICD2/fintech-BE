package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.port.SavePaymentPort
import com.inner.circle.infra.structure.repository.PaymentRepository
import com.inner.circle.infra.structure.repository.entity.PaymentEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val repository: PaymentRepository
) : SavePaymentPort {
    override fun save(request: SavePaymentPort.Request) {
        repository.save(
            PaymentEntity(
                request.userName,
                request.amount,
                request.requestAt
            )
        )
    }
}
