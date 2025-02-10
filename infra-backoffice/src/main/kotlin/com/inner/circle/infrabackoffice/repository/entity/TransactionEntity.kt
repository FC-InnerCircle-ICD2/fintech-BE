package com.inner.circle.infrabackoffice.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment_transaction")
data class TransactionEntity(
    @Id
    @Tsid
    val id: Long?,
    @Column(name = "payment_key", nullable = false)
    val paymentKey: String,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: TransactionStatus,
    val reason: String?,
    @Column(name = "requested_at", nullable = false)
    val requestedAt: LocalDateTime
) : BaseEntity()
