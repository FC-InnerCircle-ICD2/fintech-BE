package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.CreateOrUpdateMerchantKeyDto

fun interface ManagementsUseCase {
    data class CreateOrUpdateKeyRequest(
        val id: Long
    )

    fun createOrUpdateKey(request: CreateOrUpdateKeyRequest): CreateOrUpdateMerchantKeyDto
}
