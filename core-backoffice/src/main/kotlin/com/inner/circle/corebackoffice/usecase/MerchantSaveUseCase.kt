package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.MerchantDto

fun interface MerchantSaveUseCase {
    data class Request(
        val username: String,
        val password: String,
        val name: String
    )

    fun saveMerchant(request: Request): MerchantDto
}
