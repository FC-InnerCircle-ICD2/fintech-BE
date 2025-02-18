package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long> {
    fun findAllByMerchantId(
        merchantId: Long,
        pageable: Pageable
    ): Page<PaymentEntity>

    fun findByMerchantIdAndPaymentKey(
        merchantId: Long,
        paymentKey: String
    ): PaymentEntity?
}
