package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.BackofficeException
import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto
import com.inner.circle.infrabackoffice.port.MerchantSavePort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
internal class MerchantSaveAdaptor(
    private val merchantRepository: MerchantRepository
) : MerchantSavePort {
    override fun saveMerchant(request: MerchantSavePort.Request): MerchantDto {
        val merchant = merchantRepository.save(
            MerchantEntity(
                id = null,
                username = request.username,
                password = request.password,
                token = request.token,
                name = request.name
            )
        )

        return MerchantDto(
            id = merchant.id ?: throw BackofficeException.MerchantNotSaveException(),
            username = merchant.username,
            password = merchant.password,
            token = merchant.token,
            name = merchant.name
        )
    }

}
