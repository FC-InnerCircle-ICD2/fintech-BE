package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.UserCardDto

interface UserCardUseCase {
    fun save(userCard: UserCardDto): UserCardDto
    fun findByAccountId(accountId: Long): List<UserCardDto>
    fun findAll(): List<UserCardDto>
}
