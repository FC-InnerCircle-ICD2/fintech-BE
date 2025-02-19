package com.inner.circle.apibackoffice.controller.dto

data class MerchantSignInDto(
    val id: String,
    val name: String
) {
    companion object {
        fun of(
            merchant: com.inner.circle.corebackoffice.service.dto.MerchantSignInDto
        ): MerchantSignInDto =
            MerchantSignInDto(
                id = merchant.id.toString(),
                name = merchant.name
            )
    }
}
