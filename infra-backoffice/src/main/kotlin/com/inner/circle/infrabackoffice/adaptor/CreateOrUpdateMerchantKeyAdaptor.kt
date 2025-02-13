package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantKeyDto
import com.inner.circle.infrabackoffice.port.CreateOrUpdateMerchantKeyPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
internal class CreateOrUpdateMerchantKeyAdaptor(
    private val repository: MerchantRepository
) : CreateOrUpdateMerchantKeyPort {
    override fun createOrUpdateMerchantKey(
        request: CreateOrUpdateMerchantKeyPort.Request
    ): MerchantKeyDto {
        repository.findById(request.id).let {
            val merchant =
                MerchantEntity(
                    id = it.id,
                    username = it.username,
                    password = it.password,
                    token = request.token,
                    name = it.name
                )
            val savedMerchant = repository.save(merchant)
            return MerchantKeyDto(
                id = requireNotNull(savedMerchant.id),
                token = savedMerchant.token
            )
        }
    }
}
