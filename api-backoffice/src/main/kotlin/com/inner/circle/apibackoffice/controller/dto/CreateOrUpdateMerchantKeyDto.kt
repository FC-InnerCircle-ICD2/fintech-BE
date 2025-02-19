package com.inner.circle.apibackoffice.controller.dto

data class CreateOrUpdateMerchantKeyDto(
    val apiKey: String
) {
    companion object {
        fun of(
            dto: com.inner.circle.corebackoffice.service.dto.CreateOrUpdateMerchantKeyDto
        ): CreateOrUpdateMerchantKeyDto = CreateOrUpdateMerchantKeyDto(apiKey = dto.apiKey)
    }
}
