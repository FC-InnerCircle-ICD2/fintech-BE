package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, Long> {
    fun findByPaymentKey(paymentKey: String): List<TransactionEntity>
}
