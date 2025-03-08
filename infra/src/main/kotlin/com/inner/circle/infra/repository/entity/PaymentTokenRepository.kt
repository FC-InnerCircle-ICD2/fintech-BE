package com.inner.circle.infra.repository.entity

import java.time.LocalDateTime

interface PaymentTokenRepository {
    fun getPaymentDataFromToken(token: String): PaymentTokenEntity

    fun savePaymentToken(
        paymentToken: PaymentTokenEntity,
        expiresAt: LocalDateTime
    ): PaymentTokenEntity

    fun removePaymentDataByToken(token: String)

    fun getPaymentStatus(
        merchantId: String,
        orderId: String
    ): String?

    fun savePaymentStatus(
        merchantId: String,
        orderId: String,
        status: String,
        expiresAt: LocalDateTime
    )
}
