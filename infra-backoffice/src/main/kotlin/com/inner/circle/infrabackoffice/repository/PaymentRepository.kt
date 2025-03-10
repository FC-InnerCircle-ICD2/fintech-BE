package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
import java.time.LocalDate

interface PaymentRepository {
    fun findAllByMerchantIdOrderByCreatedAtDesc(
        merchantId: Long,
        paymentKey: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        limit: Int
    ): List<PaymentEntity>

    fun findByMerchantIdAndPaymentKey(
        merchantId: Long,
        paymentKey: String
    ): PaymentEntity?
}
