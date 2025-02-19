package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity

interface PaymentRepository {
    fun findAllByMerchantId(
        merchantId: Long,
        page: Int,
        limit: Int
    ): List<PaymentEntity>

    fun findByMerchantIdAndPaymentKey(
        merchantId: Long,
        paymentKey: String
    ): PaymentEntity?
}
