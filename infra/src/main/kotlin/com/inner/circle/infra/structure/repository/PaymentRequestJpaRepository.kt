package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRequestJpaRepository : JpaRepository<PaymentRequestEntity, String>  {
    fun findByOrderId(orderId: String): PaymentRequestEntity?
}
