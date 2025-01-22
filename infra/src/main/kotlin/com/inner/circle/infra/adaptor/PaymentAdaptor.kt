package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.SavePaymentPort
import com.inner.circle.infra.repository.PaymentRepository
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val repository: PaymentRepository
) : SavePaymentPort {
    override fun save(request: SavePaymentPort.Request) {
//        repository.save(
//            PaymentEntity(
//                request.userName,
//                request.amount,
//                request.requestAt
//            )
//        )
    }
}
