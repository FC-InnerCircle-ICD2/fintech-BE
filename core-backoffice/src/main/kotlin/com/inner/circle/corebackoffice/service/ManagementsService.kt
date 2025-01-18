package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.RefreshKeyDto
import com.inner.circle.corebackoffice.service.dto.toRefreshKeyDto
import com.inner.circle.corebackoffice.usecase.ManagementsUseCase
import com.inner.circle.corebackoffice.util.ClientCredentialsGenerator
import com.inner.circle.infrabackoffice.port.FindMerchantPort
import com.inner.circle.infrabackoffice.port.UpdateMerchantPort
import org.springframework.stereotype.Service

@Service
internal class ManagementsService(
    private val findMerchantPort: FindMerchantPort,
    private val updateMerchantPort: UpdateMerchantPort
) : ManagementsUseCase {
    override fun refreshKey(request: ManagementsUseCase.RefreshKeyRequest): RefreshKeyDto {
        val merchant = findMerchantPort.findById(
            FindMerchantPort.Request(id = request.id)
        )

        merchant.token = ClientCredentialsGenerator.generateClientSecret()

        return updateMerchantPort.updateMerchant(
            UpdateMerchantPort.Request(merchant = merchant)
        ).toRefreshKeyDto()
    }
}
