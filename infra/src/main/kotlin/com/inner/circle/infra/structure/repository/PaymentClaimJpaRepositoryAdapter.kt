package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

@Repository
class PaymentClaimJpaRepositoryAdapter(
    private val repository: PaymentClaimJpaRepository
) : PaymentClaimRepository {
    override fun save(entity: PaymentRequestEntity): PaymentRequestEntity = repository.save(entity)
}
