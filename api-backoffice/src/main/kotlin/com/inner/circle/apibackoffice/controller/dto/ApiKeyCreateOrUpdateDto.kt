package com.inner.circle.apibackoffice.controller.dto

data class ApiKeyCreateOrUpdateDto(
    val apiKey: String
) {
    companion object {
        fun of(
            dto: com.inner.circle.corebackoffice.service.dto.ApiKeyCreateOrUpdateDto
        ): ApiKeyCreateOrUpdateDto = ApiKeyCreateOrUpdateDto(apiKey = dto.apiKey)
    }
}
