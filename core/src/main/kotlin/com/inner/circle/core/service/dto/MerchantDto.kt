package com.inner.circle.core.service.dto

import com.inner.circle.infra.repository.entity.MerchantEntity

data class MerchantDto(
    val merchantId: Long,
    val email: String,
    val name: String
) {
    companion object {
        fun fromEntity(merchant: MerchantEntity) =
            MerchantDto(
                merchantId = merchant.id,
                email = merchant.email,
                name = merchant.name
            )
    }
}
