package com.inner.circle.infra.adaptor

import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentClaimHandlingPort
import com.inner.circle.infra.repository.entity.PaymentClaimRepository
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import org.springframework.stereotype.Component

@Component
class ClaimAdaptor(
    private val repository: PaymentClaimRepository,
    private val paymentTokenRepository: PaymentTokenRepository
) : PaymentClaimHandlingPort {
    override fun generatePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto {
        val paymentRequest = paymentRequestData.toInitGenerate(tokenData)
        // paymentRequest
        val saved = repository.save(paymentRequest)

        val tokenEntity = tokenData.toEntity()
        paymentTokenRepository.savePaymentToken(tokenEntity)

        return PaymentClaimDto.fromEntity(saved)
    }
}
