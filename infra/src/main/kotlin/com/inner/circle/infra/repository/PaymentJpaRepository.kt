package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, String> {
    fun findAllByAccountId(
        accountId: Long,
        pageable: Pageable
    ): Page<PaymentEntity>

    fun findByAccountIdAndPaymentKey(
        accountId: Long,
        paymentKey: String
    ): PaymentEntity?
}
