package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun save(paymentEntity: PaymentEntity): PaymentEntity? =
        paymentJpaRepository.saveAndFlush(paymentEntity)
}
