package com.inner.circle.core.service.dto

import com.inner.circle.infra.repository.entity.AccountEntity

data class AccountInfo(
    val id: Long
) {
    companion object {
        fun from(dto: AccountEntity): AccountInfo =
            AccountInfo(
                id = dto.id
            )
    }
}
