package com.inner.circle.infrabackoffice.structure.adaptor

import com.inner.circle.infrabackoffice.structure.port.MerchantPort
import com.inner.circle.infrabackoffice.structure.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
internal class MerchantAdaptor(
    private val repository: MerchantRepository
) : MerchantPort {
    override fun refreshToken(request: MerchantPort.RefreshRequest) {
        val merchant = repository.findById(request.id)
        merchant.token = request.token
        repository.save(merchant)
    }
}
