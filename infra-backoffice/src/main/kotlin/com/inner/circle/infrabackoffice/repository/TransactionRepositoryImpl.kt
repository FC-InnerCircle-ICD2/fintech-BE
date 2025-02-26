package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    override fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKeyIn(paymentKeys = paymentKeys)

    override fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKey(paymentKey = paymentKey)

    override fun save(transaction: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transaction)
}
