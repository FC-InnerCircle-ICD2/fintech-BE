package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentClaimHandlingPort
import com.inner.circle.infra.repository.entity.PaymentClaimRepository
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentClaimAdaptor(
    private val paymentClaimRepository: PaymentClaimRepository,
    private val paymentTokenRepository: PaymentTokenRepository
) : PaymentClaimHandlingPort {
    @Transactional
    override fun createAndSavePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto {
        val expiredAt =
            tokenData.expiredAt
                ?: throw PaymentJwtException.TokenInvalidException()

        // 결제 요청 정보 및 토큰 entity 생성
        val (paymentRequest, tokenEntity) = createPaymentRequest(paymentRequestData, tokenData)

        // paymentRequest, token entity 저장
        return savePaymentRequest(paymentRequest, tokenEntity, expiredAt)
    }

    override fun createPaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): Pair<PaymentRequestEntity, PaymentTokenEntity> {
        val paymentRequest = paymentRequestData.toInitGenerate(tokenData)
        val tokenEntity = tokenData.toEntity()
        return Pair(paymentRequest, tokenEntity)
    }

    override fun savePaymentRequest(
        paymentRequest: PaymentRequestEntity,
        tokenEntity: PaymentTokenEntity,
        expiredAt: LocalDateTime
    ): PaymentClaimDto {
        // payment request entity 저장
        val savedPaymentRequest = paymentClaimRepository.save(paymentRequest)
        // token entity 저장
        paymentTokenRepository.savePaymentToken(tokenEntity, expiredAt)
        paymentTokenRepository.savePaymentInProgress(
            tokenEntity.merchantId.toString(),
            tokenEntity.orderId,
            expiredAt
        )

        return PaymentClaimDto.fromEntity(savedPaymentRequest)
    }
}
