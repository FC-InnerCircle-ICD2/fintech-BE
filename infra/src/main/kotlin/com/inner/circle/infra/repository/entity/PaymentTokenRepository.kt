package com.inner.circle.infra.repository.entity

interface PaymentTokenRepository {
    fun getPaymentToken(
        merchantId: String,
        orderId: String
    ): PaymentTokenEntity

    fun isExpiredByToken(
        merchantId: String,
        orderId: String
    ): Boolean

    fun savePaymentToken(paymentToken: PaymentTokenEntity): PaymentTokenEntity
}
