package com.inner.circle.infrabackoffice.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "payment")
data class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    @Column(nullable = false, unique = true)
    val paymentKey: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    val userId: UUID?,
    val merchantId: UUID?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType
) : BaseEntity()
