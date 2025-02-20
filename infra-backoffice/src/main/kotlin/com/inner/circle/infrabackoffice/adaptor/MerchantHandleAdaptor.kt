package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.port.MerchantHandlePort
import com.inner.circle.infrabackoffice.repository.ApiKeyRepository
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
internal class MerchantHandleAdaptor(
    private val apiKeyRepository: ApiKeyRepository,
    private val merchantRepository: MerchantRepository
) : MerchantHandlePort {
    override fun findMerchantByToken(token: String): MerchantEntity {
        val apiKey = apiKeyRepository.findByToken(token)
        return merchantRepository.findById(apiKey.merchantId)
    }
}
