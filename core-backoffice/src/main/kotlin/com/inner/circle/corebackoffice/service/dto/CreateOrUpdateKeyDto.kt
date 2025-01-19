package com.inner.circle.corebackoffice.service.dto

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

class CreateOrUpdateKeyDto(
    val key: String
)

fun MerchantEntity.toCreateOrUpdateKeyDto(): CreateOrUpdateKeyDto =
    CreateOrUpdateKeyDto(
        key = this.token
    )
