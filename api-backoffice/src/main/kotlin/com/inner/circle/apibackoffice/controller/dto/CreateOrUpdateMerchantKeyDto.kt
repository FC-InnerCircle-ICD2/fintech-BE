package com.inner.circle.apibackoffice.controller.dto

data class CreateOrUpdateMerchantKeyDto(
    val key: String
) {
    companion object {
        fun of(
            dto: com.inner.circle.corebackoffice.service.dto.CreateOrUpdateMerchantKeyDto
        ): CreateOrUpdateMerchantKeyDto = CreateOrUpdateMerchantKeyDto(key = dto.key)
    }
}
