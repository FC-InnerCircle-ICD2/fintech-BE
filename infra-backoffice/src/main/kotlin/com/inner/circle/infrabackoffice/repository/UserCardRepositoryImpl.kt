package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.UserCardEntity
import org.springframework.stereotype.Repository

@Repository
internal class UserCardRepositoryImpl(
    private val userCardJpaRepository: UserCardJpaRepository
) : UserCardRepository {
    override fun findAll(): List<UserCardEntity> = userCardJpaRepository.findAll()

    override fun findByAccountId(accountId: Long): List<UserCardEntity> =
        userCardJpaRepository.findByAccountId(accountId)

    override fun save(userCardEntity: UserCardEntity): UserCardEntity? =
        userCardJpaRepository.save(userCardEntity)
}
