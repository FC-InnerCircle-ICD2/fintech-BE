package com.inner.circle.infra.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "payment")
data class PaymentEntity(
    @Id
    @Tsid
    val id: Long?,
    @Column(name = "payment_key", nullable = false)
    val paymentKey: String,
    @Column(name = "card_number")
    val cardNumber: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    @Column(name = "account_id")
    val accountId: Long?,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: Long,
    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,
    @Column(name = "order_id", nullable = false)
    val orderId: String,
    @Column(name = "order_name")
    val orderName: String?
) : BaseEntity()
