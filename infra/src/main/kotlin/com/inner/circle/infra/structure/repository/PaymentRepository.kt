package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentEntity

fun interface PaymentRepository {
    fun save(entity: PaymentEntity): PaymentEntity?
}
