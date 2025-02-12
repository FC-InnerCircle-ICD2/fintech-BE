package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.UserCardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserCardJpaRepository : JpaRepository<UserCardEntity, String> {
    fun findByAccountId(accountId: Long): List<UserCardEntity>
}
