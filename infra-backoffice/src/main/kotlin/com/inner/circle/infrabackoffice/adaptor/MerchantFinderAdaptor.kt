package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
class MerchantFinderAdaptor(
    private val merchantRepository: MerchantRepository
) : MerchantFinderPort {
    override fun existsByEmail(email: String): Boolean = merchantRepository.existsByEmail(email)

    override fun findByEmailOrNull(email: String): MerchantEntity? =
        merchantRepository.findByEmailOrNull(email)

    override fun findByIdOrNull(id: Long): MerchantEntity? = merchantRepository.findByIdOrNull(id)
}
