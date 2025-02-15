package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.AccountInfo

fun interface UserLoginUseCase {
    data class UserLoginInfo(
        val email: String,
        val password: String
    )

    fun findValidAccountOrThrow(loginInfo: UserLoginInfo): AccountInfo
}
