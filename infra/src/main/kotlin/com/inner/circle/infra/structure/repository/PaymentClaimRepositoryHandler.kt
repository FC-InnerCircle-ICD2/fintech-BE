package com.inner.circle.infra.structure.repository

import org.springframework.stereotype.Repository

@Repository
internal class PaymentClaimRepositoryHandler(
    private val repository: PaymentClaimJpaRepository
) : PaymentClaimRepository {
    override fun save(entity: PaymentClaimEntity): PaymentClaimEntity = repository.save(entity)

    override fun findByOrderId(orderId: String): PaymentClaimEntity? =
        repository.findByOrderId(orderId).firstOrNull()
}
