package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

data class MerchantSignInDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(dto: MerchantEntity): MerchantSignInDto =
            MerchantSignInDto(
                id = dto.id,
                name = dto.name
            )
    }
}
