package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.ApiKeyEntity

interface ApiKeyRepository {
    fun findByMerchantId(merchantId: Long): ApiKeyEntity

    fun findByToken(token: String): ApiKeyEntity
}
