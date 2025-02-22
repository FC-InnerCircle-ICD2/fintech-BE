package com.inner.circle.infra.repository.entity

import com.inner.circle.exception.PaymentClaimException
import com.inner.circle.infra.repository.PaymentClaimJpaRepository
import org.springframework.stereotype.Repository

@Repository
internal class PaymentClaimRepositoryHandler(
    private val repository: PaymentClaimJpaRepository
) : PaymentClaimRepository {
    override fun save(entity: PaymentRequestEntity): PaymentRequestEntity {
        val existingEntity =
            repository.findByMerchantIdAndOrderId(
                entity.merchantId,
                entity.orderId
            )
        if (existingEntity != null) {
            throw PaymentClaimException.ClaimAlreadyExistsException(entity.orderId)
        }
        return repository.save(entity)
    }

    override fun findByOrderId(orderId: String): PaymentRequestEntity? =
        repository.findByOrderId(orderId).firstOrNull()
}
