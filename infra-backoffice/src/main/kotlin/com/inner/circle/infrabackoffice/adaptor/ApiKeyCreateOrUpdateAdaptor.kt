package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.ApiKeyDto
import com.inner.circle.infrabackoffice.port.ApiKeyCreateOrUpdatePort
import com.inner.circle.infrabackoffice.repository.ApiKeyRepository
import com.inner.circle.infrabackoffice.repository.entity.ApiKeyEntity
import org.springframework.stereotype.Component

@Component
internal class ApiKeyCreateOrUpdateAdaptor(
    private val repository: ApiKeyRepository
) : ApiKeyCreateOrUpdatePort {
    override fun createOrUpdateApiKey(request: ApiKeyCreateOrUpdatePort.Request): ApiKeyDto {
        val apiKey =
            when (val existingKey = repository.findByMerchantId(request.merchantId)) {
                null -> ApiKeyEntity(merchantId = request.merchantId, apiKey = request.apiKey)
                else -> existingKey.apply { apiKey = request.apiKey }
            }
        return repository.save(apiKey).let {
            ApiKeyDto(apiKey = it.apiKey)
        }
    }
}
