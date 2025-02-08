package com.inner.circle.infra.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
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
    val status: String,
    val reason: String?,
    @Column(name = "request_time", nullable = false)
    var requestTime: LocalDateTime?,
    @Column(name = "completion_time")
    var completionTime: LocalDateTime?
) : BaseEntity()
