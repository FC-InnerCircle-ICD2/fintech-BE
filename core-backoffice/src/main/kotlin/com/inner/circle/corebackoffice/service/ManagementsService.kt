package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.CreateOrUpdateKeyDto
import com.inner.circle.corebackoffice.usecase.ManagementsUseCase
import com.inner.circle.corebackoffice.util.ClientCredentialsGenerator
import com.inner.circle.infrabackoffice.port.CreateOrUpdateMerchantKeyPort
import org.springframework.stereotype.Service

@Service
internal class ManagementsService(
    private val createOrUpdateMerchantKeyPort: CreateOrUpdateMerchantKeyPort,
    private val clientCredentialsGenerator: ClientCredentialsGenerator
) : ManagementsUseCase {
    override fun createOrUpdateKey(
        request: ManagementsUseCase.CreateOrUpdateKeyRequest
    ): CreateOrUpdateKeyDto {
        val merchant =
            createOrUpdateMerchantKeyPort
                .createOrUpdateMerchantKey(
                    request = CreateOrUpdateMerchantKeyPort.Request(
                        id = request.id,
                        token = clientCredentialsGenerator.generateClientSecret()
                    )
                )
        return CreateOrUpdateKeyDto(key = merchant.token)
    }
}
