package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import java.util.UUID

interface TransactionRepository {
    fun save(transaction: TransactionEntity): TransactionEntity

    fun findByPaymentId(id: UUID): List<TransactionEntity>
}
