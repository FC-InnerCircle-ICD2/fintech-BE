package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity

interface TransactionRepository {
    fun save(transactionEntity: TransactionEntity): TransactionEntity?
}
