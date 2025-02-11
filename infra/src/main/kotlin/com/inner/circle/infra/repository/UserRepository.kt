package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<AccountEntity, Long>
