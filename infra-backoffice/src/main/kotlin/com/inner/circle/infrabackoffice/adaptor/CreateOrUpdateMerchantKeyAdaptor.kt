package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantKeyDto
import com.inner.circle.infrabackoffice.port.CreateOrUpdateMerchantKeyPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
internal class CreateOrUpdateMerchantKeyAdaptor(
    private val repository: MerchantRepository
) : CreateOrUpdateMerchantKeyPort {
    override fun createOrUpdateMerchantKey(
        request: CreateOrUpdateMerchantKeyPort.Request
    ): MerchantKeyDto {
        val merchant = repository.findById(request.id)
        merchant.token = request.token
        val result = repository.save(merchant)
        return MerchantKeyDto(
            id = result.id ?: "",
            token = result.token,
        )
    }
}
