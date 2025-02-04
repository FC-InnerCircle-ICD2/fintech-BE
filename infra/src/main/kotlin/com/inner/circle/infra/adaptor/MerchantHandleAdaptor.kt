package com.inner.circle.infra.adaptor

import com.inner.circle.infra.port.MerchantHandlePort
import com.inner.circle.infra.repository.MerchantRepository
import com.inner.circle.infra.repository.entity.MerchantEntity
import org.springframework.stereotype.Component

@Component
internal class MerchantHandleAdaptor(
    private val merchantRepository: MerchantRepository
) : MerchantHandlePort {
    override fun findMerchantByKey(key: String): MerchantEntity {
        return merchantRepository.findByToken(key)
    }
}