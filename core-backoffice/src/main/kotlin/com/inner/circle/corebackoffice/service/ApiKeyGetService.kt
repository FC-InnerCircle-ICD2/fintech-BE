package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.ApiKeyGetDto
import com.inner.circle.corebackoffice.usecase.ApiKeyGetUseCase
import com.inner.circle.infrabackoffice.port.ApiKeyFinderPort
import org.springframework.stereotype.Service

@Service
class ApiKeyGetService(
    private val apiKeyFinderPort: ApiKeyFinderPort
) : ApiKeyGetUseCase {
    override fun getApiKeyByMerchantId(request: ApiKeyGetUseCase.Request): ApiKeyGetDto =
        apiKeyFinderPort.findByMerchantId(request.merchantId).let {
            ApiKeyGetDto(apiKey = it.apiKey)
        }
}
