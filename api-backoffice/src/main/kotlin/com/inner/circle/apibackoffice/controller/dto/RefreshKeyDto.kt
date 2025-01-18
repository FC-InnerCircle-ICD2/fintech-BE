package com.inner.circle.apibackoffice.controller.dto

data class RefreshKeyDto(
    val key: String
) {
    companion object {
        fun of(
            refreshKeyDto: com.inner.circle.corebackoffice.service.dto.RefreshKeyDto
        ): RefreshKeyDto = RefreshKeyDto(key = refreshKeyDto.key)
    }
}
