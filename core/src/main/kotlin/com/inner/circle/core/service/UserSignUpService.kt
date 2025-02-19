package com.inner.circle.core.service

import com.inner.circle.core.usecase.UserSignUpUseCase
import com.inner.circle.infra.port.AccountSavePort
import org.springframework.stereotype.Service

@Service
class UserSignUpService(
    private val accountSavePort: AccountSavePort
) : UserSignUpUseCase {
    override fun signUp(account: UserSignUpUseCase.UserSignUpInfo) {
        accountSavePort.save(
            AccountSavePort.AccountSaveInfo(
                email = account.email,
                password = account.password
            )
        )
    }
}
