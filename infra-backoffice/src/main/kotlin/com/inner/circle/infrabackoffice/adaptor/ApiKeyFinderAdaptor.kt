package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.ApiKeyFinderDto
import com.inner.circle.infrabackoffice.port.ApiKeyFinderPort
import com.inner.circle.infrabackoffice.repository.ApiKeyRepository
import org.springframework.stereotype.Component

@Component
class ApiKeyFinderAdaptor(
    private val apiKeyRepository: ApiKeyRepository
) : ApiKeyFinderPort {
    override fun findByMerchantId(merchantId: Long): ApiKeyFinderDto {
        val apiKey = apiKeyRepository.findByMerchantId(merchantId)
        return ApiKeyFinderDto(
            apiKey = apiKey?.token
        )
    }
}
