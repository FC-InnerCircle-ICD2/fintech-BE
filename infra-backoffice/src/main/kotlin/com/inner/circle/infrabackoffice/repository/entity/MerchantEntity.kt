package com.inner.circle.infrabackoffice.repository.entity

import io.hypersistence.tsid.TSID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "merchant")
data class MerchantEntity(
    @Id
    val id: Long = TSID.fast().toLong(),
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val name: String
) : BaseEntity()
