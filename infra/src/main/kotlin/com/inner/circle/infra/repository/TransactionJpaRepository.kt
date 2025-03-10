package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, String> {
    fun findAllByPaymentKeyInOrderByCreatedAtDesc(
        paymentKeys: List<String>
    ): List<TransactionEntity>

    fun findAllByPaymentKeyOrderByCreatedAtDesc(paymentKey: String): List<TransactionEntity>
}
