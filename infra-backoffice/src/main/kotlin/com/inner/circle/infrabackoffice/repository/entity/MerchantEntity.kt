package com.inner.circle.infrabackoffice.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "merchant")
data class MerchantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String?,
    @Column(nullable = false, unique = true)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(unique = true)
    val token: String,
    @Column(nullable = false)
    val name: String
) : BaseEntity()
