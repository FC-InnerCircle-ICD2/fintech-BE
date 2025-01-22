package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity

interface PaymentRequestRepository {
    fun findByOrderIdAndMerchantId(orderId: String, merchantId: String): PaymentRequestEntity?

    fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity?
}
