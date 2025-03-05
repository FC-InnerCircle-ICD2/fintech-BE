package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserCardJpaRepository : JpaRepository<UserCardEntity, String> {
    fun findByAccountIdAndIsRepresentative(
        accountId: Long,
        isRepresentative: Boolean
    ): UserCardEntity?

    fun findByAccountIdOrderByIdDesc(accountId: Long): List<UserCardEntity>

    fun findAllByOrderByAccountIdAscIdDesc(): List<UserCardEntity>

    fun findById(id: Long): UserCardEntity

    fun deleteById(id: Long)
}
