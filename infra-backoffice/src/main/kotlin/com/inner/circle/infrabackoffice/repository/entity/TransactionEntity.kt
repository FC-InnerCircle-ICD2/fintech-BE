package com.inner.circle.infrabackoffice.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "payment_transaction")
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    @Column(nullable = false)
    val paymentId: UUID,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    val status: TransactionStatus,
    @Column(nullable = false)
    val paymentKey: String,
    val reason: String?,
    @Column(nullable = false)
    val requestedAt: LocalDateTime,
    @Column(nullable = false)
    val completedAt: LocalDateTime
) : BaseEntity()
