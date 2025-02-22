package com.inner.circle.infrabackoffice.adaptor.dto

import com.google.common.hash.Hashing
import com.inner.circle.infrabackoffice.repository.entity.PaymentTokenEntity
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

data class PaymentTokenDto(
    val merchantId: Long,
    val orderId: String,
    val generatedToken: String,
    val expiredAt: LocalDateTime?,
    val signature: String
) {
    fun toEntity(): PaymentTokenEntity =
        PaymentTokenEntity(
            merchantId = merchantId,
            orderId = orderId,
            generatedToken =
                Hashing
                    .murmur3_128()
                    .hashString(
                        generatedToken,
                        StandardCharsets.UTF_8
                    ).toString(),
            signature = signature
        )
}
