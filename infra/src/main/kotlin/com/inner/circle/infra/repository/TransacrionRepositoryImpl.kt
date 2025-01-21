package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransacrionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    override fun save(transactionEntity: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transactionEntity)
}
