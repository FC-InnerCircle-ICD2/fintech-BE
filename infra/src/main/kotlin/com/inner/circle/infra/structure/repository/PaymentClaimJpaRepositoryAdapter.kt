package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class PaymentClaimJpaRepositoryAdapter(
    private val repository: PaymentClaimJpaRepository
) : PaymentClaimRepository {
    override fun save(entity: PaymentRequestEntity): PaymentRequestEntity = repository.save(entity)

    fun findById(orderId: String): PaymentRequestEntity? = repository.findByIdOrNull(orderId)
}
