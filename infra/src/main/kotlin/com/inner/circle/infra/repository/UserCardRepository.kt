package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity

interface UserCardRepository {
    fun findByAccountId(accountId: Long): UserCardEntity?

    fun findByAccountIdAndIsRepresentative(accountId: Long): UserCardEntity?

    fun save(userCardEntity: UserCardEntity): UserCardEntity?
}
