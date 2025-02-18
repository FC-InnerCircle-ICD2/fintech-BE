package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.UserCardDto

interface UserCardUseCase {
    fun save(userCard: UserCardDto): UserCardDto

    fun findByAccountId(accountId: Long): List<UserCardDto>

    fun findAll(): List<UserCardDto>

    fun updateRepresentativeCard(
        accountId: Long,
        id: Long
    ): List<UserCardDto>

    fun deleteById(
        accountId: Long,
        id: Long
    ): UserCardDto
}
