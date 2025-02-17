package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.UserCardDto

interface UserCardPort {
    fun save(userCard: UserCardDto): UserCardDto

    fun findByAccountId(accountId: Long): List<UserCardDto>

    fun findAll(): List<UserCardDto>

    fun findById(id: Long): UserCardDto

    fun deleteById(
        accountId: Long,
        id: Long
    ): UserCardDto

    fun findByAccountIdAndIsRepresentative(
        accountId: Long,
        isRepresentative: Boolean
    ): UserCardDto

    fun saveAll(userCardDtoList: List<UserCardDto>): List<UserCardDto>
}
