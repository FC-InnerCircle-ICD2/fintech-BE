package com.inner.circle.infra.repository.entity

import com.inner.circle.infra.type.AccountStatus
import io.hypersistence.tsid.TSID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    val id: Long = TSID.fast().toLong(),
    @Column(nullable = false, unique = true, length = 255)
    val email: String,
    @Column(nullable = false, unique = false, length = 255)
    val password: String,
    @Column(nullable = false, unique = false)
    val status: Int = AccountStatus.ACTIVE.code,
) : BaseEntity()
