package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.structure.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import com.inner.circle.infra.structure.repository.PaymentClaimRepository
import org.springframework.stereotype.Component

@Component
class ClaimAdaptor(
    private val repository: PaymentClaimRepository
) : ClaimHandlingPort {
    override fun generatePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto {
        val paymentRequest = paymentRequestData.toInitGenerate()
        val saved = repository.save(paymentRequest)
        return PaymentClaimDto.fromEntity(saved)
    }
}
