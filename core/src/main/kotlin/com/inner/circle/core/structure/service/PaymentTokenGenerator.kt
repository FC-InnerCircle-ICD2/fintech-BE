package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.domain.PaymentToken
import io.hypersistence.tsid.TSID
import java.time.LocalDateTime

private const val EXPIRED_LIMIT = 3L

class PaymentTokenGenerator {
    fun generateToken(
        orderId: String,
        ttlMinutes: Long
    ): PaymentToken {
        val paddedOrderId = orderId.padEnd(13, '0').take(13)
        val tsid = TSID.from(paddedOrderId)
        val token = tsid.toString()
        val expiredAt = LocalDateTime.now().plusMinutes(EXPIRED_LIMIT)
        return PaymentToken(token, expiredAt)
    }
}
