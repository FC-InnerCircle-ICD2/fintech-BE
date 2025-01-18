package com.inner.circle.apibackoffice.controller.dto

data class CreateOrUpdateKeyDto(
    val key: String
) {
    companion object {
        fun of(
            dto: com.inner.circle.corebackoffice.service.dto.CreateOrUpdateKeyDto
        ): CreateOrUpdateKeyDto = CreateOrUpdateKeyDto(key = dto.key)
    }
}
