package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity

interface PaymentRepository {
    fun save(paymentEntity: PaymentEntity): PaymentEntity?
}
