package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.MerchantDto

fun interface MerchantSaveUseCase {
    data class Request(
        val email: String,
        val password: String,
        val name: String
    )

    fun save(request: Request): MerchantDto
}
