package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.ApiKeyCreateOrUpdateDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.ApiKeyCreateOrUpdateUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Management", description = "Management API")
@BackofficeV1Api
class ManagementController(
    private val apiKeyCreateOrUpdateUseCase: ApiKeyCreateOrUpdateUseCase
) {
    @Operation(summary = "API 키 발급 및 갱신")
    @PostMapping("/key")
    fun createOrUpdateKey(
        @RequestBody request: ApiKeyCreateOrUpdateUseCase.CreateOrUpdateKeyRequest
    ): BackofficeResponse<ApiKeyCreateOrUpdateDto> {
        val response =
            ApiKeyCreateOrUpdateDto.of(
                apiKeyCreateOrUpdateUseCase.createOrUpdateKey(request)
            )
        return BackofficeResponse.ok(response)
    }

    @Operation(summary = "API 키 조회")
    @GetMapping("/key/{id}")
    fun getKey() {
    }
}
