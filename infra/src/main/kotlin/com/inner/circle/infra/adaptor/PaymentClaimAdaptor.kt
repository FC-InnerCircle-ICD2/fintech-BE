package com.inner.circle.infra.adaptor

import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentClaimHandlingPort
import com.inner.circle.infra.repository.entity.PaymentClaimRepository
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentClaimAdaptor(
    private val repository: PaymentClaimRepository,
    private val paymentTokenRepository: PaymentTokenRepository
) : PaymentClaimHandlingPort {
    @Transactional
    override fun handlePaymentRequestGeneration(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto {
        // payment request entity 구성
        val paymentRequest = paymentRequestData.toInitGenerate(tokenData)

        // payment request entity 저장
        val saved = repository.save(paymentRequest)

        // token entity 구성
        val tokenEntity = tokenData.toEntity()

        // token entity 저장
        paymentTokenRepository.savePaymentToken(tokenEntity)

        return PaymentClaimDto.fromEntity(saved)
    }
}
