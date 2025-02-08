package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity

interface TransactionRepository {
    fun save(transaction: TransactionEntity): TransactionEntity

    fun findByPaymentKey(paymentKey: String): List<TransactionEntity>
}
