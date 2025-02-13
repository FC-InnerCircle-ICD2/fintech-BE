package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.MerchantSignInDto

interface MerchantSignInUseCase {
    data class Request(
        val username: String,
        val password: String
    )

    fun signInMerchant(request: Request): MerchantSignInDto
}
