package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto

fun interface MerchantSavePort {
    data class Request(
        val email: String,
        val password: String,
        val name: String
    )

    fun save(request: Request): MerchantDto
}
