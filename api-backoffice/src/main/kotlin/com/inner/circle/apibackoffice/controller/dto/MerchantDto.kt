package com.inner.circle.apibackoffice.controller.dto

data class MerchantDto(
    val id: String,
    val username: String,
    val password: String,
    val token: String,
    val name: String
) {
    companion object {
        fun of(merchant: com.inner.circle.corebackoffice.service.dto.MerchantDto): MerchantDto =
            MerchantDto(
                id = merchant.id,
                username = merchant.username,
                password = merchant.password,
                token = merchant.token,
                name = merchant.name
            )
    }
}
