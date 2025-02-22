package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

data class MerchantDto(
    val id: Long,
    val email: String,
    val password: String,
    val name: String
) {
    companion object {
        fun fromEntity(merchant: MerchantEntity) =
            MerchantDto(
                id = merchant.id,
                email = merchant.email,
                password = merchant.password,
                name = merchant.name
            )
    }
}
