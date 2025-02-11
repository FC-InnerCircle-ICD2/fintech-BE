package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "merchant")
data class MerchantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,
    @Column(nullable = false)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false, unique = true)
    val mid: String,
    @Comment("client_secret")
    @Column(nullable = false, unique = true)
    var token: String,
    @Column(nullable = false)
    val name: String
) : BaseEntity()
