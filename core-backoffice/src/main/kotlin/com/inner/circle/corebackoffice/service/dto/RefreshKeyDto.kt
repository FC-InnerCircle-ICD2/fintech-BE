package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

class RefreshKeyDto(
    val key: String
)

fun MerchantEntity.toRefreshKeyDto(): RefreshKeyDto {
    return RefreshKeyDto(
        key = this.token
    )
}
