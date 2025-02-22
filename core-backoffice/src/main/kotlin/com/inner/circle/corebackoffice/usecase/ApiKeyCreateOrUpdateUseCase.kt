package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.ApiKeyCreateOrUpdateDto

fun interface ApiKeyCreateOrUpdateUseCase {
    data class CreateOrUpdateKeyRequest(
        val id: Long
    )

    fun createOrUpdateKey(request: CreateOrUpdateKeyRequest): ApiKeyCreateOrUpdateDto
}
