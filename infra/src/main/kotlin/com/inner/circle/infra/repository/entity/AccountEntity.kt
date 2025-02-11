package com.inner.circle.infra.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    @Tsid
    val id: Long,
    @Column(nullable = false, unique = true, length = 255)
    val email: String,
    @Column(nullable = false, unique = false, length = 255)
    val password: String,
    @Column(nullable = false, unique = false)
    val status: Int
) : BaseEntity()
