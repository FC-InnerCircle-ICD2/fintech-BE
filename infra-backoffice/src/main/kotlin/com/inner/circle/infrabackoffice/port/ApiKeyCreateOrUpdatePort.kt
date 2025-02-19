package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.ApiKeyDto

fun interface ApiKeyCreateOrUpdatePort {
    data class Request(
        val merchantId: Long,
        val apiKey: String
    )

    fun createOrUpdateApiKey(request: Request): ApiKeyDto
}
