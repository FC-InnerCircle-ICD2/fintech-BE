package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.AccountEntity

interface AccountFinderPort {
    fun findByIdOrNull(id: Long): AccountEntity?

    fun findByEmailOrNull(email: String): AccountEntity?
}
