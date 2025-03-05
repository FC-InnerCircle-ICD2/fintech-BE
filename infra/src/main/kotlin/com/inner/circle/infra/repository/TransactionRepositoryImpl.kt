package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    override fun save(transactionEntity: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transactionEntity)

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
}
