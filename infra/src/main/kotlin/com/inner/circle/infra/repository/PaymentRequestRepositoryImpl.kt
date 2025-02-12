package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRequestRepositoryImpl(
    private val paymentRequestJpaRepository: PaymentRequestJpaRepository
) : PaymentRequestRepository {
    override fun findByOrderIdAndMerchantId(
        orderId: String,
        merchantId: Long
    ): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByOrderIdAndMerchantId(orderId, merchantId)

    override fun findByPaymentKeyAndOrderId(
        paymentKey: String,
        orderId: String
    ): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByPaymentKeyAndOrderId(paymentKey, orderId)

    override fun findByOrderId(orderId: String): PaymentRequestEntity? =
        paymentRequestJpaRepository.findByOrderId(orderId)

    override fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity? =
        paymentRequestJpaRepository.save(paymentRequestEntity)
}
