package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.port.SavePaymentPort
import com.inner.circle.infra.structure.repository.TestPaymentRepository
import org.springframework.stereotype.Component

internal class TestPaymentAdaptor(
    private val repository: TestPaymentRepository
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
