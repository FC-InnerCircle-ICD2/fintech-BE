package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import java.time.LocalDate

interface PaymentRepository {
    fun save(paymentEntity: PaymentEntity): PaymentEntity?

    fun findAllByAccountId(
        accountId: Long,
        startDate: LocalDate?,
        endDate: LocalDate?,
        page: Int,
        limit: Int
    ): List<PaymentEntity>

    fun findByAccountIdAndPaymentKey(
        accountId: Long,
        paymentKey: String
    ): PaymentEntity?
}
