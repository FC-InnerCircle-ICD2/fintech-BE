package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.port.FindMerchantPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
internal class FindMerchantAdaptor(
    private val repository: MerchantRepository
) : FindMerchantPort {
    override fun findById(request: FindMerchantPort.Request) = repository.findById(request.id)
}
