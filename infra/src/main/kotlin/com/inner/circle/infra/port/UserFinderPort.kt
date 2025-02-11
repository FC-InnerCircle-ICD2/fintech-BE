package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.AccountEntity

fun interface UserFinderPort {
    fun findByIdOrNull(id: String): AccountEntity?
}
