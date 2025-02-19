package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.ApiKeyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ApiKeyJpaRepository : JpaRepository<ApiKeyEntity, Long> {
    fun findByMerchantId(merchantId: Long): ApiKeyEntity?

    fun findByToken(token: String): ApiKeyEntity?
}
