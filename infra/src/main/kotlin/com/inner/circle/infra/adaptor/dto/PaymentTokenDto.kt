package com.inner.circle.infra.adaptor.dto

import com.inner.circle.infra.repository.entity.PaymentTokenEntity
import java.time.LocalDateTime

data class PaymentTokenDto(
    val merchantId: Long,
    val orderId: String,
    val generatedToken: String,
    val expiredAt: LocalDateTime
) {
    fun toEntity(): PaymentTokenEntity =
        PaymentTokenEntity(
            merchantId = merchantId,
            orderId = orderId,
            generatedToken = generatedToken
        )
}
