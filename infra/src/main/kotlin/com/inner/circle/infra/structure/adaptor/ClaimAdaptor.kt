package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.port.ClaimHandlingPort
import com.inner.circle.infra.structure.repository.PaymentRequestRepository
import com.inner.circle.infra.structure.repository.entity.PaymentRequest
import org.springframework.stereotype.Component

@Component
internal class ClaimAdaptor(
    private val repository: PaymentRequestRepository
) : ClaimHandlingPort {
    override fun generatePaymentRequest(paymentRequest: PaymentRequest): PaymentRequest? =
        repository.save(paymentRequest)
}
