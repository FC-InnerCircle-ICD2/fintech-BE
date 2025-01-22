package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRequestJpaRepository : JpaRepository<PaymentRequestEntity, String> {
    fun findByOrderIdAndMerchantId(orderId: String, merchantId: String): PaymentRequestEntity?
}
