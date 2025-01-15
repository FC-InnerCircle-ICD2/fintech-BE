package com.inner.circle.infrabackoffice.structure.repository

import com.inner.circle.infrabackoffice.structure.repository.entity.AccountEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository : JpaRepository<AccountEntity, UUID>
