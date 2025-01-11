package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    val currency: String,
    @Column(name = "user_id")
    val userId: Long?,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: String,
    @Column(name = "payment_type", nullable = false)
    val paymentType: String
) : BaseEntity() {
    companion object {
        fun createWithId(
            currency: String,
            userId: Long?,
            merchantId: String,
            paymentType: String,
            id: Long
        ): PaymentEntity =
            PaymentEntity(
                id = id,
                currency = currency,
                userId = userId,
                merchantId = merchantId,
                paymentType = paymentType
            )
    }
}
