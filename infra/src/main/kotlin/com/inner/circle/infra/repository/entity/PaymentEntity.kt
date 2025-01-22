package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payment")
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
) : com.inner.circle.infra.repository.entity.BaseEntity() {
    companion object {
        fun createWithId(
            currency: String,
            userId: Long?,
            merchantId: String,
            paymentType: String,
            id: Long
        ): com.inner.circle.infra.repository.entity.PaymentEntity =
            com.inner.circle.infra.repository.entity.PaymentEntity(
                id = id,
                currency = currency,
                userId = userId,
                merchantId = merchantId,
                paymentType = paymentType
            )
    }
}
