package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentClaimEntity

interface PaymentClaimRepository {
    fun save(entity: PaymentClaimEntity): PaymentClaimEntity

    fun findByOrderId(orderId: String): PaymentClaimEntity?
}
