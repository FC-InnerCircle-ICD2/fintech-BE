package com.inner.circle.infra.structure.adaptor

import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.structure.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import com.inner.circle.infra.structure.repository.PaymentClaimRepository
import com.inner.circle.infra.structure.repository.PaymentTokenRepository
import org.springframework.stereotype.Component

@Component
class ClaimAdaptor(
    private val repository: PaymentClaimRepository,
    private val paymentTokenRepository: PaymentTokenRepository
) : ClaimHandlingPort {
    override fun generatePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto {
        val paymentRequest = paymentRequestData.toInitGenerate(tokenData)
        // paymentRequest
        val saved = repository.save(paymentRequest)

        val tokenEntity = tokenData.toEntity()

        return PaymentClaimDto.fromEntity(saved)
    }
}
