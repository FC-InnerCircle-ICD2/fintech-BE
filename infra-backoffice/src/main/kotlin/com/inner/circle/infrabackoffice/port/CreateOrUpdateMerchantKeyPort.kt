package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto

fun interface CreateOrUpdateMerchantKeyPort {
    data class Request(
        val id: String,
        val token: String
    )

    fun createOrUpdateMerchantKey(request: Request): MerchantDto
}
