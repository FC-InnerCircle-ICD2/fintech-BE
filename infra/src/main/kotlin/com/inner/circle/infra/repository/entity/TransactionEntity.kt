package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment_transaction")
data class TransactionEntity(
    @Id
    @Column(name = "payment_key")
    val paymentKey: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    val payment: PaymentEntity,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    val status: String,
    val reason: String?,
    @Column(name = "request_time", nullable = false)
    var requestTime: LocalDateTime?,
    @Column(name = "completion_time")
    var completionTime: LocalDateTime?
) : BaseEntity() {
    @PrePersist
    fun prePersist() {
        if (requestTime == null) {
            requestTime = LocalDateTime.now()
        }
        if (completionTime == null) {
            completionTime = LocalDateTime.now()
        }
    }
}
