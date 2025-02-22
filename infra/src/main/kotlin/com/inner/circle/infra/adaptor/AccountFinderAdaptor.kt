package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.AccountFinderPort
import com.inner.circle.infra.repository.AccountRepository
import com.inner.circle.infra.repository.entity.AccountEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AccountFinderAdaptor(
    private val accountRepository: AccountRepository
) : AccountFinderPort {
    override fun findByIdOrNull(id: Long): AccountEntity? = accountRepository.findByIdOrNull(id)

    override fun findByEmailOrNull(email: String): AccountEntity? =
        accountRepository.findByEmail(email)
}
