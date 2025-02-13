package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
class MerchantFinderAdaptor(
    private val merchantRepository: MerchantRepository
) : MerchantFinderPort {
    override fun findByUsername(username: String): MerchantEntity? =
        merchantRepository.findByUsername(username)

    override fun findByUsernamePassword(
        username: String,
        password: String
    ): MerchantEntity = merchantRepository.findByUsernameAndPassword(username, password)
}
