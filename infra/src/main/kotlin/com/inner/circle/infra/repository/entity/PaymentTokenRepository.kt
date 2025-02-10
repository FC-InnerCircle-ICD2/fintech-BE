package com.inner.circle.infra.repository.entity

import java.time.LocalDateTime

interface PaymentTokenRepository {
    fun getPaymentToken(
        merchantId: String,
        orderId: String
    ): PaymentTokenEntity

    fun savePaymentToken(
        paymentToken: PaymentTokenEntity,
        expiresAt: LocalDateTime
    ): PaymentTokenEntity
}
