package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import java.time.LocalDateTime

interface PaymentClaimHandlingPort {
    fun createAndSavePaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): PaymentClaimDto

    fun createPaymentRequest(
        paymentRequestData: PaymentClaimDto,
        tokenData: PaymentTokenDto
    ): Pair<PaymentRequestEntity, PaymentTokenEntity>

    fun savePaymentRequest(
        paymentRequest: PaymentRequestEntity,
        tokenEntity: PaymentTokenEntity,
        expiredAt: LocalDateTime
    ): PaymentClaimDto
}
