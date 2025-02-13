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
        val newMerchant = repository.findById(request.id).copy(token = request.token)
        val savedMerchant = repository.save(newMerchant)
        return MerchantKeyDto(
            id = requireNotNull(savedMerchant.id),
            token = savedMerchant.token
        )
    }
}
