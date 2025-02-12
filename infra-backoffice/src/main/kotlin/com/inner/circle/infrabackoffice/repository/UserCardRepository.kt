package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.UserCardEntity

interface UserCardRepository {
    fun findAll(): List<UserCardEntity>

    fun findByAccountId(accountId: Long): List<UserCardEntity>

    fun save(userCardEntity: UserCardEntity): UserCardEntity?
}
