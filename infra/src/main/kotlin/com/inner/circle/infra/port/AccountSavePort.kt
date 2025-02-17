package com.inner.circle.infra.port

interface AccountSavePort {
    data class AccountSaveInfo(
        val email: String,
        val password: String
    )

    fun saveAccount(account: AccountSaveInfo)
}
