package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.RefreshKeyDto

fun interface ManagementsUseCase {
    data class RefreshKeyRequest(
        val id: String
    )

    fun refreshKey(request: RefreshKeyRequest): RefreshKeyDto
}
