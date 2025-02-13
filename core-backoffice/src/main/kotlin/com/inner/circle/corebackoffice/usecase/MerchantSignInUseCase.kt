package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.MerchantSignInDto

fun interface MerchantSignInUseCase {
    data class Request(
        val username: String,
        val password: String
    )

    fun signIn(request: Request): MerchantSignInDto
}
