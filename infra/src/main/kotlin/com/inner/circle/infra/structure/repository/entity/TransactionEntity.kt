package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    val payment: PaymentEntity,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    val status: String,
    @Column(name = "payment_key", nullable = false, unique = true)
    val paymentKey: String,
    val reason: String?,
    @Column(name = "request_time", nullable = false)
    val requestTime: LocalDateTime,
    @Column(name = "completion_time")
    val completionTime: LocalDateTime?
) : BaseEntity()
