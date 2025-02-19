package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.ApiKeyEntity

interface ApiKeyRepository {
    fun findByMerchantId(merchantId: Long): ApiKeyEntity?

    fun save(apiKey: ApiKeyEntity): ApiKeyEntity
}
