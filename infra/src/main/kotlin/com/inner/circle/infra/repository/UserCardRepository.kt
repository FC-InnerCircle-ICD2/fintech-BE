package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity

interface UserCardRepository {
    fun findByAccountIdAndIsRepresentative(
        accountId: Long,
        isRepresentative: Boolean
    ): UserCardEntity?

    fun findByAccountId(accountId: Long): List<UserCardEntity>

    fun findAll(): List<UserCardEntity>

    fun findByAccountIdAndIsRepresentative(accountId: Long): UserCardEntity?

    fun save(userCardEntity: UserCardEntity): UserCardEntity?
}
