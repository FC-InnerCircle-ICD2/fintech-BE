package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, String> {
    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity>

    fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity>
}
