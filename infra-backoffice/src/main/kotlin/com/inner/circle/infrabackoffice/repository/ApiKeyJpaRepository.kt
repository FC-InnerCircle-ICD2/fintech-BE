package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.ApiKeyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ApiKeyJpaRepository : JpaRepository<ApiKeyEntity, Long> {
    fun findByMerchantId(merchantId: Long): ApiKeyEntity?

    fun findByToken(token: String): ApiKeyEntity?
}
