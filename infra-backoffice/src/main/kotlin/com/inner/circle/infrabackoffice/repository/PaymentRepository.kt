package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.PaymentEntity

interface PaymentRepository {
    fun save(payment: PaymentEntity): PaymentEntity

    fun findByPaymentKey(paymentKey: String): PaymentEntity?
}
