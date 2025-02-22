package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.ApiKeyGetDto

fun interface ApiKeyGetUseCase {
    data class Request(
        val merchantId: Long
    )

    fun getApiKeyByMerchantId(request: Request): ApiKeyGetDto
}
