package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "userCard")
data class UserCardEntity(
    @Id
    val id: String,
    @Column(nullable = false)
    val userId: Long?,
    @Column(nullable = false)
    val representativeYn: Boolean,
    @Column(nullable = false, unique = true)
    val cardNumber: String,
    @Column(nullable = false)
    val expirationPeriod: String,
    @Column(nullable = false)
    val cvc: String
) : BaseEntity()
