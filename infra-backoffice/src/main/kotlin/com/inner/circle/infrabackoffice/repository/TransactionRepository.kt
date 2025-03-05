package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity

interface TransactionRepository {
    fun findAllByPaymentKeyInOrderByCreatedAtDesc(
        paymentKeys: List<String>
    ): List<TransactionEntity>

    fun findAllByPaymentKeyOrderByCreatedAtDesc(paymentKey: String): List<TransactionEntity>

    fun save(transaction: TransactionEntity): TransactionEntity
}
