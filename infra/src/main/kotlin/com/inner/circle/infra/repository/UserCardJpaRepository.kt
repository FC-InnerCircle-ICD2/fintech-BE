package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserCardJpaRepository : JpaRepository<UserCardEntity, String> {
    fun findByAccountId(accountId: Long): UserCardEntity?
}
