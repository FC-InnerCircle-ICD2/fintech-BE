package com.inner.circle.apibackoffice.controller.dto

data class MerchantSignInDto(
    val id: String,
    val token: String,
    val name: String
) {
    companion object {
        fun of(
            merchant: com.inner.circle.corebackoffice.service.dto.MerchantSignInDto
        ): MerchantSignInDto =
            MerchantSignInDto(
                id = merchant.id.toString(),
                token = merchant.token,
                name = merchant.name
            )
    }
}
