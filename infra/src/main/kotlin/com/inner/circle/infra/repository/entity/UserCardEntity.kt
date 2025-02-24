package com.inner.circle.infra.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_card")
data class UserCardEntity(
    @Id
    @Tsid
    @Column
    val id: Long?,
    @Column(nullable = false)
    val accountId: Long,
    @Column(nullable = false)
    val isRepresentative: Boolean,
    @Column(nullable = false, unique = true)
    val cardNumber: String,
    @Column(nullable = false)
    val expirationPeriod: String,
    @Column(nullable = false)
    val cvc: String,
    @Column(nullable = false)
    val cardCompany: String
) : BaseEntity()
