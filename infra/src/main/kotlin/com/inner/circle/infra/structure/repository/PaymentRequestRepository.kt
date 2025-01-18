package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity

interface PaymentRequestRepository {
    fun findByOrderId(orderId: String): PaymentRequestEntity?
}
