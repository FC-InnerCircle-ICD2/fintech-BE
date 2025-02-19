package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantKeyDto

fun interface CreateOrUpdateMerchantKeyPort {
    data class Request(
        val merchantId: Long,
        val apiKey: String
    )

    fun createOrUpdateMerchantKey(request: Request): MerchantKeyDto
}
