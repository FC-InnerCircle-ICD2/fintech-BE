package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentTokenEntity

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
