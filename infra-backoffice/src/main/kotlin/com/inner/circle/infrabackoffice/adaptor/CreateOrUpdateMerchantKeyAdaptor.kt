package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantKeyDto
import com.inner.circle.infrabackoffice.port.CreateOrUpdateMerchantKeyPort
import com.inner.circle.infrabackoffice.repository.ApiKeyRepository
import com.inner.circle.infrabackoffice.repository.entity.ApiKeyEntity
import org.springframework.stereotype.Component

@Component
internal class CreateOrUpdateMerchantKeyAdaptor(
    private val repository: ApiKeyRepository
) : CreateOrUpdateMerchantKeyPort {
    override fun createOrUpdateMerchantKey(
        request: CreateOrUpdateMerchantKeyPort.Request
    ): MerchantKeyDto {
        val apiKey =
            when (val existingKey = repository.findByMerchantId(request.merchantId)) {
                null -> ApiKeyEntity(merchantId = request.merchantId, apiKey = request.apiKey)
                else -> existingKey.apply { apiKey = request.apiKey }
            }
        return repository.save(apiKey).let {
            MerchantKeyDto(apiKey = it.apiKey)
        }
    }
}
