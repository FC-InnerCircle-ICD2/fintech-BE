package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, String> {
    fun findByToken(token: String): MerchantEntity?
}
