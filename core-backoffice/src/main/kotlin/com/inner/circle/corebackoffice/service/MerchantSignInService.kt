package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.MerchantSignInDto
import com.inner.circle.corebackoffice.usecase.MerchantSignInUseCase
import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import org.springframework.stereotype.Service

@Service
class MerchantSignInService(
    private val merchantFinderPort: MerchantFinderPort
) : MerchantSignInUseCase {
    override fun signInMerchant(request: MerchantSignInUseCase.Request): MerchantSignInDto {
        val merchant =
            merchantFinderPort.findByUsernamePassword(
                username = request.username,
                password = request.password
            )

        return MerchantSignInDto(
            id = merchant.id ?: "",
            token = merchant.token,
            name = merchant.name
        )
    }
}
