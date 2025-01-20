package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentEntity

fun interface TestPaymentRepository {
    fun save(entity: PaymentEntity): PaymentEntity?
}
