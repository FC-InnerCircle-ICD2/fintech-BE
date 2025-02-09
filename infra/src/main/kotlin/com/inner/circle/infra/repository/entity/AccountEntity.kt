package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false, unique = true)
    val uuid: UUID,
    @Column(nullable = false, unique = true, length = 255)
    val email: String,
    @Column(nullable = false, unique = false, length = 255)
    val password: String,
    @Column(nullable = false, unique = false, length = 1)
    val status: Int,
) : BaseEntity()
