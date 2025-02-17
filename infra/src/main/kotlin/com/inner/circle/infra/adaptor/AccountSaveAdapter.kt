package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.AccountSavePort
import com.inner.circle.infra.repository.UserRepository
import com.inner.circle.infra.repository.entity.AccountEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountSaveAdapter(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
): AccountSavePort {
    override fun saveAccount(account: AccountSavePort.AccountSaveInfo) {
        userRepository.save(
            account.toEntity()
        )
    }

    private fun AccountSavePort.AccountSaveInfo.toEntity(): AccountEntity =
        AccountEntity(
            email = email,
            password = bCryptPasswordEncoder.encode(password),
        )
}

