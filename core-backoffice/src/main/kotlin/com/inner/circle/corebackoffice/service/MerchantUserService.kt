package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.MerchantDto
import com.inner.circle.corebackoffice.usecase.MerchantUserHandleUseCase
import com.inner.circle.infrabackoffice.port.MerchantHandlePort
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
