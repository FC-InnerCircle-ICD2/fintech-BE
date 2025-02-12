package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.UserCardDto

interface UserCardPort {
    fun save(userCard: UserCardDto): UserCardDto
    fun findByAccountId(accountId: Long): List<UserCardDto>
    fun findAll(): List<UserCardDto>
}
