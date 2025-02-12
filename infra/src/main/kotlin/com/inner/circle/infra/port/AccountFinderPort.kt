package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.AccountEntity

fun interface AccountFinderPort {
    fun findByIdOrNull(id: Long): AccountEntity?
}
