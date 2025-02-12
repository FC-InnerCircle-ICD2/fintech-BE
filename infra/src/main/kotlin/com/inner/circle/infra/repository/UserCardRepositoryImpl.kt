package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity
import org.springframework.stereotype.Repository

@Repository
internal class UserCardRepositoryImpl(
    private val userCardJpaRepository: UserCardJpaRepository
) : UserCardRepository {
    override fun findByAccountId(accountId: Long): UserCardEntity? =
        userCardJpaRepository.findByAccountId(accountId)

    override fun findByAccountIdAndIsRepresentative(accountId: Long): UserCardEntity? {
        return userCardJpaRepository.findByAccountIdAndIsRepresentative(accountId, true)
    }

    override fun save(userCardEntity: UserCardEntity): UserCardEntity? =
        userCardJpaRepository.save(userCardEntity)
}
