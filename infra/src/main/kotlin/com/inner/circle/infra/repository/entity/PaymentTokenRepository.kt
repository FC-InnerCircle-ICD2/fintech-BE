package com.inner.circle.infra.repository.entity

import java.time.LocalDateTime

interface PaymentTokenRepository {
    fun getPaymentDataFromToken(token: String): PaymentTokenEntity

    fun savePaymentToken(
        paymentToken: PaymentTokenEntity,
        expiresAt: LocalDateTime
    ): PaymentTokenEntity
}
