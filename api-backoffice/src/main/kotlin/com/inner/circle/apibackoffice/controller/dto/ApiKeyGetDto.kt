package com.inner.circle.apibackoffice.controller.dto

data class ApiKeyGetDto(
    val apiKey: String?
) {
    companion object {
        fun of(dto: com.inner.circle.corebackoffice.service.dto.ApiKeyGetDto): ApiKeyGetDto =
            ApiKeyGetDto(apiKey = dto.apiKey)
    }
}
