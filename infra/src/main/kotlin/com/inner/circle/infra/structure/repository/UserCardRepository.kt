package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.UserCardEntity

interface UserCardRepository {
    fun findByUserId(userId: Long?): UserCardEntity?
}
