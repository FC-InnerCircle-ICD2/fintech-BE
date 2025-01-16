package com.inner.circle.infrabackoffice.structure.repository

import com.inner.circle.infrabackoffice.structure.repository.entity.AccountEntity
import org.springframework.stereotype.Repository

@Repository
internal class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository
) : AccountRepository {
    override fun save(account: AccountEntity): AccountEntity = accountJpaRepository.save(account)
}
