package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.port.UpdateMerchantPort
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import org.springframework.stereotype.Component

@Component
internal class UpdateMerchantAdaptor(
    private val repository: MerchantRepository
) : UpdateMerchantPort {
    override fun updateMerchant(request: UpdateMerchantPort.Request) = repository.save(request.merchant)
}
