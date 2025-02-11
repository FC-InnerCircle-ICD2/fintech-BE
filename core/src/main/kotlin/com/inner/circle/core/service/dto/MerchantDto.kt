package com.inner.circle.core.service.dto

import com.inner.circle.infra.repository.entity.MerchantEntity

data class MerchantDto(
    val merchantId: String,
    val username: String,
    val name: String
) {
    companion object {
        fun fromEntity(merchant: MerchantEntity) =
            MerchantDto(
                merchantId = merchant.id.toString(),
                username = merchant.username,
                name = merchant.name
            )
    }
}
