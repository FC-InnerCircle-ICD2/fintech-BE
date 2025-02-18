package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantKeyDto

fun interface CreateOrUpdateMerchantKeyPort {
    data class Request(
        val id: Long,
        val token: String
    )

    fun createOrUpdateMerchantKey(request: Request): MerchantKeyDto
}
