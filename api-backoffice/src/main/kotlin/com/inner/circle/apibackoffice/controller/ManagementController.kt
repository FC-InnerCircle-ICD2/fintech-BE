package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.config.SwaggerConfig
import com.inner.circle.apibackoffice.controller.dto.ApiKeyCreateOrUpdateDto
import com.inner.circle.apibackoffice.controller.dto.ApiKeyGetDto
import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.corebackoffice.security.MerchantUserDetails
import com.inner.circle.corebackoffice.usecase.ApiKeyCreateOrUpdateUseCase
import com.inner.circle.corebackoffice.usecase.ApiKeyGetUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Tag(name = "Management", description = "Management API")
@BackofficeV1Api
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class ManagementController(
    private val apiKeyCreateOrUpdateUseCase: ApiKeyCreateOrUpdateUseCase,
    private val apiKeyGetUseCase: ApiKeyGetUseCase
) {
    @Operation(summary = "API 키 발급 및 갱신")
    @PostMapping("/keys")
    fun createOrUpdateKey(
        @AuthenticationPrincipal merchant: MerchantUserDetails
    ): BackofficeResponse<ApiKeyCreateOrUpdateDto> {
        val request = ApiKeyCreateOrUpdateUseCase.CreateOrUpdateKeyRequest(id = merchant.getId())
        val response =
            ApiKeyCreateOrUpdateDto.of(
                apiKeyCreateOrUpdateUseCase.createOrUpdateKey(request)
            )
        return BackofficeResponse.ok(response)
    }

    @Operation(summary = "API 키 조회")
    @GetMapping("/my-key")
    fun getKey(
        @AuthenticationPrincipal merchant: MerchantUserDetails
    ): BackofficeResponse<ApiKeyGetDto> {
        val response =
            ApiKeyGetDto.of(
                apiKeyGetUseCase.getApiKeyByMerchantId(
                    ApiKeyGetUseCase.Request(merchantId = merchant.getId())
                )
            )
        return BackofficeResponse.ok(response)
    }
}
