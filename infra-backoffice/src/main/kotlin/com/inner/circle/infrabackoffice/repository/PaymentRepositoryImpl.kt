package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity
import org.springframework.stereotype.Repository

@Repository
internal class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository
) : PaymentRepository {
    // TODO: Payment쪽에 있어야할 로직이지만, 당분간 테스트를 위해 여기에 둠
    override fun save(payment: PaymentEntity): PaymentEntity = paymentJpaRepository.save(payment)

    override fun findByPaymentKey(paymentKey: String): PaymentEntity? =
        paymentJpaRepository.findByPaymentKey(paymentKey)
}
