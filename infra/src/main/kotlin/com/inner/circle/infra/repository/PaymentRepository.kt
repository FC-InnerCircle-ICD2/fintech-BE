package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import java.time.LocalDate

interface PaymentRepository {
    fun save(paymentEntity: PaymentEntity): PaymentEntity?

    fun findAllByAccountIdOrderByCreatedAtDesc(
        accountId: Long,
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
