package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity

interface TransactionRepository {
    fun save(transactionEntity: TransactionEntity): TransactionEntity?

    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity>

    fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity>
}
