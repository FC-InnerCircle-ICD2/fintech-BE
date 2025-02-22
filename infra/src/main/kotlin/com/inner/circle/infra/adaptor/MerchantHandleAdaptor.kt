package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.MerchantHandlePort
import com.inner.circle.infra.repository.ApiKeyRepository
import com.inner.circle.infra.repository.MerchantRepository
import com.inner.circle.infra.repository.entity.MerchantEntity
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
