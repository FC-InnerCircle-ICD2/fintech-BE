package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity

fun interface PaymentRepository {
    fun save(entity: com.inner.circle.infra.repository.entity.PaymentEntity): com.inner.circle.infra.repository.entity.PaymentEntity?
}
