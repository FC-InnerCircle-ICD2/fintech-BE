package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto
import com.inner.circle.infrabackoffice.port.CreateOrUpdateMerchantKeyPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
internal class CreateOrUpdateMerchantKeyAdaptor(
    private val repository: MerchantRepository
) : CreateOrUpdateMerchantKeyPort {
    override fun createOrUpdateMerchantKey(
        request: CreateOrUpdateMerchantKeyPort.Request
    ): MerchantDto {
        val merchant = repository.findById(request.id)
        merchant.token = request.token
        val result = repository.save(merchant)
        return MerchantDto(
            id = result.id,
            mid = result.mid,
            token = result.token,
            name = result.name
        )
    }
}
