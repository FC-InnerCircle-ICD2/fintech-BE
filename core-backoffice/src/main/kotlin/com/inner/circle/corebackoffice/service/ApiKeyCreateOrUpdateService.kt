package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.ApiKeyCreateOrUpdateDto
import com.inner.circle.corebackoffice.usecase.ApiKeyCreateOrUpdateUseCase
import com.inner.circle.corebackoffice.util.ClientCredentialsGenerator
import com.inner.circle.infrabackoffice.port.ApiKeyCreateOrUpdatePort
import org.springframework.stereotype.Service

@Service
internal class ApiKeyCreateOrUpdateService(
    private val apiKeyCreateOrUpdatePort: ApiKeyCreateOrUpdatePort,
    private val clientCredentialsGenerator: ClientCredentialsGenerator
) : ApiKeyCreateOrUpdateUseCase {
    override fun createOrUpdateKey(
        request: ApiKeyCreateOrUpdateUseCase.CreateOrUpdateKeyRequest
    ): ApiKeyCreateOrUpdateDto {
        val dto =
            apiKeyCreateOrUpdatePort
                .createOrUpdateApiKey(
                    request =
                        ApiKeyCreateOrUpdatePort.Request(
                            merchantId = request.id,
                            token = clientCredentialsGenerator.generateClientSecret()
                        )
                )
        return ApiKeyCreateOrUpdateDto(apiKey = dto.apiKey)
    }
}
