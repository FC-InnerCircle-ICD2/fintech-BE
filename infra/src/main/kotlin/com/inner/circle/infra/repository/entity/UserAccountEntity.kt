package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

data class UserAccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    @Column(nullable = false, unique = true, length = 255)
    val email: String,
    @Column(nullable = false, unique = false, length = 255)
    val password: String,
    @Column(nullable = false, unique = false)
    val status: Int
) : BaseEntity()
