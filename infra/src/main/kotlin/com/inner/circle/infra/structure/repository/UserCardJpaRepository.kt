package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.UserCardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserCardJpaRepository : JpaRepository<UserCardEntity, String> {
    fun findByUserId(userId: Long?): UserCardEntity?
}
