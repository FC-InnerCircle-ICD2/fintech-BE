package com.inner.circle.corebackoffice.structure.service

import com.inner.circle.corebackoffice.structure.usecase.ManagementsUseCase
import com.inner.circle.corebackoffice.util.ClientCredentialsGenerator
import com.inner.circle.infrabackoffice.structure.port.MerchantPort
import org.springframework.stereotype.Service

@Service
internal class ManagementsService(
    private val merchantPort: MerchantPort
) : ManagementsUseCase {
    override fun refreshKey(request: ManagementsUseCase.RefreshKeyRequest) {
        merchantPort.refreshToken(
            MerchantPort.RefreshRequest(
                id = request.id,
                token = ClientCredentialsGenerator.generateClientSecret()
            )
        )
    }
}
