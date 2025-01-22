package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Id

data class MerchantEntity(
    @Id
    val id: String,
    @Column(nullable = false, unique = true)
    val mid: String,
    @Column(nullable = false, unique = true)
    val token: String,
    @Column(nullable = false)
    val name: String
) : BaseEntity()
