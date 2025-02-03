package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentRequestEntity

interface PaymentRequestRepository {
    fun findByPaymentKeyAndOrderId(
        paymentKey: String,
        orderId: String
    ): PaymentRequestEntity?

    fun findByOrderIdAndMerchantId(
        orderId: String,
        merchantId: String
    ): PaymentRequestEntity?

    fun findByOrderId(orderId: String): PaymentRequestEntity?

    fun save(paymentRequestEntity: PaymentRequestEntity): PaymentRequestEntity?
}
