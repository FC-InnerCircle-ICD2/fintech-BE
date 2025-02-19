package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    override fun save(transactionEntity: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transactionEntity)

    override fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKeyIn(paymentKeys = paymentKeys)

    override fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKey(paymentKey = paymentKey)
}
