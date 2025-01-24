package com.inner.circle.infra.repository.entity

interface PaymentClaimRepository {
    fun save(entity: PaymentRequestEntity): PaymentRequestEntity

    fun findByOrderId(orderId: String): PaymentRequestEntity?
}
