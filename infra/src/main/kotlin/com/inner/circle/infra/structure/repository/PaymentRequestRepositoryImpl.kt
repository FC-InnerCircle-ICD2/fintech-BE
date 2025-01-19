package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRequestRepositoryImpl(
    private val paymentRequestJpaRepository: PaymentRequestJpaRepository
) : PaymentRequestRepository {
    override fun findByOrderId(orderId: String): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByOrderId(orderId)

    override fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity? =
        paymentRequestJpaRepository.save(paymentRequestEntity)
}
