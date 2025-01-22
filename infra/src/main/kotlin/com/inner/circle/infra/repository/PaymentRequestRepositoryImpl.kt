package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRequestRepositoryImpl(
    private val paymentRequestJpaRepository: PaymentRequestJpaRepository
) : PaymentRequestRepository {
    override fun findByOrderIdAndMerchantId(orderId: String, merchantId: String): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByOrderIdAndMerchantId(orderId, merchantId)

    override fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity? =
        paymentRequestJpaRepository.save(paymentRequestEntity)
}
