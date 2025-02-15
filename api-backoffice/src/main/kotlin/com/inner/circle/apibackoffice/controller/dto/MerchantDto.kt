package com.inner.circle.apibackoffice.controller.dto

import com.inner.circle.corebackoffice.service.dto.MerchantDto as CoreMerchantDto

data class MerchantDto(
    val id: String,
    val username: String,
    val token: String,
    val name: String
) {
    companion object {
        fun of(merchant: CoreMerchantDto): MerchantDto =
            MerchantDto(
                id = merchant.id,
                username = merchant.username,
                token = merchant.token,
                name = merchant.name
            )
    }
}
