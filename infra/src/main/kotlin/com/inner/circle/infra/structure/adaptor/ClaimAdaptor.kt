package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.adaptor.dto.PaymentRequestDto
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import com.inner.circle.infra.structure.repository.PaymentClaimRepository
import org.springframework.stereotype.Component

@Component
class ClaimAdaptor(
    private val repository: PaymentClaimRepository
) : ClaimHandlingPort {
    override fun generatePaymentRequest(paymentRequestData: PaymentRequestDto): PaymentRequestDto {
        val paymentRequest = paymentRequestData.toEntity()
        repository.save(paymentRequest)
        return PaymentRequestDto.fromEntity(paymentRequest)
    }
}
