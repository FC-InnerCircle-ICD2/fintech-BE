package com.inner.circle.core.service

import com.inner.circle.core.service.dto.AccountInfo
import com.inner.circle.core.usecase.UserLoginUseCase
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.port.AccountFinderPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserLoginService(
    private val accountFinderPort: AccountFinderPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserLoginUseCase {
    override fun findValidAccountOrThrow(loginInfo: UserLoginUseCase.UserLoginInfo): AccountInfo =
        accountFinderPort
            .findByEmailOrNull(
                email = loginInfo.email
            )?.let { accountEntity ->
                accountEntity
                    .takeIf { bCryptPasswordEncoder.matches(loginInfo.password, it.password) }
                    ?.let { AccountInfo.from(dto = it) }
                    ?: throw UserAuthenticationException.InvalidPassword()
            } ?: throw UserAuthenticationException.UserNotFoundException()
}
