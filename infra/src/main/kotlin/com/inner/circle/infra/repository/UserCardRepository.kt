package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity

interface UserCardRepository {
    fun findByUserId(userId: Long?): UserCardEntity?

    fun save(userCardEntity: UserCardEntity): UserCardEntity?
}
