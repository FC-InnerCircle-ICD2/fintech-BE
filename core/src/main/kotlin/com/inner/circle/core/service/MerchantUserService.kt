package com.inner.circle.core.service

import com.inner.circle.core.service.dto.MerchantDto
import com.inner.circle.core.usecase.MerchantUserHandleUseCase
import com.inner.circle.infra.port.MerchantHandlePort
import org.springframework.stereotype.Service

@Service
class MerchantUserService(
    private val merchantHandlePort: MerchantHandlePort
) : MerchantUserHandleUseCase {
    override fun findMerchantUser(token: String): MerchantDto {
        val findMerchantByKey = merchantHandlePort.findMerchantByToken(token)
        return MerchantDto.fromEntity(findMerchantByKey)
    }
}
