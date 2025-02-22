package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto
import com.inner.circle.infrabackoffice.port.MerchantSavePort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
internal class MerchantSaveAdaptor(
    private val merchantRepository: MerchantRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : MerchantSavePort {
    override fun save(request: MerchantSavePort.Request): MerchantDto {
        val merchant =
            merchantRepository.save(
                MerchantEntity(
                    email = request.email,
                    password = bCryptPasswordEncoder.encode(request.password),
                    name = request.name
                )
            )

        return MerchantDto(
            id = merchant.id,
            email = merchant.email,
            password = merchant.password,
            name = merchant.name
        )
    }
}
