package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.AccountSavePort
import com.inner.circle.infra.repository.AccountRepository
import com.inner.circle.infra.repository.entity.AccountEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountSaveAdaptor(
    private val accountRepository: AccountRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : AccountSavePort {
    override fun save(account: AccountSavePort.AccountSaveInfo) {
        accountRepository.save(
            account.toEntity()
        )
    }

    private fun AccountSavePort.AccountSaveInfo.toEntity(): AccountEntity =
        AccountEntity(
            email = email,
            password = bCryptPasswordEncoder.encode(password)
        )
}
