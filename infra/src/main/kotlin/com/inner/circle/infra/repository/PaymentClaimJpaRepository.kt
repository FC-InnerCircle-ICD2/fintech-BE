package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentClaimJpaRepository : JpaRepository<PaymentRequestEntity, Long> {
    fun findByOrderId(orderId: String): MutableList<PaymentRequestEntity>

    fun findByMerchantIdAndOrderId(
        merchantId: Long,
        orderId: String
    ): PaymentRequestEntity?
}
