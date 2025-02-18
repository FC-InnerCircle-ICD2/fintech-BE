package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    override fun findAllByMerchantId(
        merchantId: Long,
        page: Int,
        limit: Int
    ): List<PaymentEntity> =
        paymentJpaRepository
            .findAllByMerchantId(
                merchantId = merchantId,
                pageable = PageRequest.of(page, limit)
            ).content

    override fun findByMerchantIdAndPaymentKey(
        merchantId: Long,
        paymentKey: String
    ): PaymentEntity? =
        paymentJpaRepository.findByMerchantIdAndPaymentKey(
            merchantId = merchantId,
            paymentKey = paymentKey
        )
}
