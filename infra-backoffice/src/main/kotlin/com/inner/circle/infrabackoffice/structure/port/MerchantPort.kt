package com.inner.circle.infrabackoffice.structure.port

fun interface MerchantPort {
    data class RefreshRequest(
        val id: String,
        val token: String
    )

    fun refreshToken(request: RefreshRequest)
}
