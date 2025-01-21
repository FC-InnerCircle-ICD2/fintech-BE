package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentClaimEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentClaimJpaRepository : JpaRepository<PaymentClaimEntity, Long> {
    fun findByOrderId(orderId: String): MutableList<PaymentClaimEntity>
}
