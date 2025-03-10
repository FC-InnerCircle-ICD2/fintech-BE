package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    override fun findAllByPaymentKeyInOrderByCreatedAtDesc(
        paymentKeys: List<String>
    ): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKeyInOrderByCreatedAtDesc(
            paymentKeys = paymentKeys
        )

    override fun findAllByPaymentKeyOrderByCreatedAtDesc(
        paymentKey: String
    ): List<TransactionEntity> =
        transactionJpaRepository.findAllByPaymentKeyOrderByCreatedAtDesc(paymentKey = paymentKey)

    override fun save(transaction: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transaction)
}
