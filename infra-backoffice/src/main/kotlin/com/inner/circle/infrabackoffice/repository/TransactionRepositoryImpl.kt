package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import org.springframework.stereotype.Repository

@Repository
internal class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {
    // TODO: Payment쪽에 있어야할 로직이지만, 당분간 테스트를 위해 여기에 둠
    override fun save(transaction: TransactionEntity): TransactionEntity =
        transactionJpaRepository.save(transaction)

    override fun findByPaymentKey(paymentKey: String): List<TransactionEntity> =
        transactionJpaRepository.findByPaymentKey(paymentKey)
}
