package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.CreateOrUpdateKeyDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.ManagementsUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Management", description = "Management API")
@RestController
@RequestMapping("/api/managements/v1")
class ManagementsController(
    private val managementsUseCase: ManagementsUseCase
) {
    @Operation(summary = "API 키 발급 및 갱신")
    @PostMapping("/keys")
    fun createOrUpdateKey(
        @RequestBody request: ManagementsUseCase.CreateOrUpdateKeyRequest
    ): BackofficeResponse<CreateOrUpdateKeyDto> {
        val response = CreateOrUpdateKeyDto.of(managementsUseCase.createOrUpdateKey(request))
        return BackofficeResponse.ok(response)
    }
}
