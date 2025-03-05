package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity

interface TransactionRepository {
    fun save(transactionEntity: TransactionEntity): TransactionEntity

    fun findAllByPaymentKeyInOrderByCreatedAtDesc(
        paymentKeys: List<String>
    ): List<TransactionEntity>

    fun findAllByPaymentKeyOrderByCreatedAtDesc(paymentKey: String): List<TransactionEntity>
}
