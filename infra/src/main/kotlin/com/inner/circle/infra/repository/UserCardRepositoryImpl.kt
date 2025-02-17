package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.UserCardEntity
import org.springframework.stereotype.Repository

@Repository
internal class UserCardRepositoryImpl(
    private val userCardJpaRepository: UserCardJpaRepository
) : UserCardRepository {
    override fun findByAccountIdAndIsRepresentative(
        accountId: Long,
        isRepresentative: Boolean
    ): UserCardEntity? =
        userCardJpaRepository.findByAccountIdAndIsRepresentative(
            accountId,
            isRepresentative
        )

    override fun save(userCardEntity: UserCardEntity): UserCardEntity =
        userCardJpaRepository.save(userCardEntity)

    override fun findAll(): List<UserCardEntity> =
        userCardJpaRepository.findAllByOrderByAccountIdAscIdAsc()

    override fun findByAccountId(accountId: Long): List<UserCardEntity> =
        userCardJpaRepository.findByAccountIdOrderByIdAsc(accountId)

    override fun findById(id: Long): UserCardEntity =
        userCardJpaRepository.findById(id)

    override fun deleteById(id: Long) =
        userCardJpaRepository.deleteById(id)

    override fun saveAll(userCardEntityList: List<UserCardEntity>): List<UserCardEntity> =
        userCardJpaRepository.saveAll(userCardEntityList)
}
