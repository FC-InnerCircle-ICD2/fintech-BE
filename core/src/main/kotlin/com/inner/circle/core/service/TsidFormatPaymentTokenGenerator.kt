package com.inner.circle.core.service

import com.inner.circle.core.domain.PaymentToken
import io.hypersistence.tsid.TSID
import java.time.LocalDateTime
import org.springframework.stereotype.Component

private const val REQUIRED_TSID_LENGTH = 13

@Component
class TsidFormatPaymentTokenGenerator {
    fun generateTsidToken(
        orderId: String,
        ttlMinutes: Long
    ): PaymentToken {
        val paddedOrderId = orderId.padEnd(REQUIRED_TSID_LENGTH, '0').take(REQUIRED_TSID_LENGTH)
        val tsid = TSID.from(paddedOrderId)
        val token = tsid.toString()
        val expiredAt = LocalDateTime.now().plusMinutes(ttlMinutes)
        return PaymentToken(token, expiredAt)
    }
}
