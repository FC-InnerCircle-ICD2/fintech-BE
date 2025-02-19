package com.inner.circle.infra.repository.entity

import io.hypersistence.tsid.TSID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "api_key")
data class ApiKeyEntity(
    @Id
    val id: Long = TSID.fast().toLong(),
    @Column(name = "merchant_id", nullable = false, unique = true)
    val merchantId: Long,
    @Column(name = "api_key", unique = true)
    var apiKey: String
) : BaseEntity()
