package com.inner.circle.infra.structure.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentClaimJpaRepository : JpaRepository<PaymentClaimEntity, String> {
    fun findByOrderId(orderId: String): MutableList<PaymentClaimEntity>
}
