package com.inner.circle.infra.structure.repository

interface PaymentClaimRepository {
    fun save(entity: PaymentClaimEntity): PaymentClaimEntity

    fun findByOrderId(orderId: String): PaymentClaimEntity?
}
