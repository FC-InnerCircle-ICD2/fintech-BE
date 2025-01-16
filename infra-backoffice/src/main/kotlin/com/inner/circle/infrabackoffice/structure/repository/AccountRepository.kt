package com.inner.circle.infrabackoffice.structure.repository

import com.inner.circle.infrabackoffice.structure.repository.entity.AccountEntity

fun interface AccountRepository {
    fun save(account: AccountEntity): AccountEntity
}
