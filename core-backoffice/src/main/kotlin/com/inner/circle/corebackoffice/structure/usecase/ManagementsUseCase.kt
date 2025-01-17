package com.inner.circle.corebackoffice.structure.usecase

fun interface ManagementsUseCase {
    data class RefreshKeyRequest(
        val id: String
    )

    fun refreshKey(request: RefreshKeyRequest)
}
