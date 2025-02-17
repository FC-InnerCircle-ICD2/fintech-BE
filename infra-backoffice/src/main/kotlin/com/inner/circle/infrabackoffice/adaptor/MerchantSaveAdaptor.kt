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
    override fun save(request: MerchantSavePort.Request): MerchantDto {
        val merchant =
            merchantRepository.save(
                MerchantEntity(
                    email = request.email,
                    password = request.password,
                    token = request.token,
                    name = request.name
                )
            )

        return MerchantDto(
            id = merchant.id ?: throw BackofficeException.MerchantNotSaveException(),
            email = merchant.email,
            password = merchant.password,
            token = merchant.token,
            name = merchant.name
        )
    }
}
