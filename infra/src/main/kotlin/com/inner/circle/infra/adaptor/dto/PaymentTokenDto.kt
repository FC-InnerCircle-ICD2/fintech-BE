package com.inner.circle.infra.adaptor.dto

import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import java.time.LocalDateTime

data class PaymentTokenDto(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String,
    val expiresAt: LocalDateTime
) {
    fun toEntity(): PaymentTokenEntity =
        PaymentTokenEntity(
            merchantId = merchantId,
            orderId = orderId,
            generatedToken = generatedToken,
            expiresAt = expiresAt
        )
}
