package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto
import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
class MerchantFinderAdaptor(
    private val merchantRepository: MerchantRepository
) : MerchantFinderPort {
    override fun existsByEmail(email: String): Boolean = merchantRepository.existsByEmail(email)

    override fun findByUsernameAndPassword(
        email: String,
        password: String
    ): MerchantDto {
        val merchant = merchantRepository.findByUsernameAndPassword(email, password)
        return MerchantDto(
            id = merchant.id,
            email = merchant.email,
            password = merchant.password,
            name = merchant.name
        )
    }
}
