package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, Long> {
    fun findAllByPaymentKeyIn(paymentKeys: List<String>): List<TransactionEntity>

    fun findAllByPaymentKey(paymentKey: String): List<TransactionEntity>
}
