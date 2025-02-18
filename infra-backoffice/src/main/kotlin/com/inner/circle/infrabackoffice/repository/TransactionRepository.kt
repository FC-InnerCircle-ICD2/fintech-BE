package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity

interface TransactionRepository {
    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity>

    fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity>
}
