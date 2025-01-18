package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.RefreshKeyDto
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.ManagementsUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/managements/v1")
class ManagementsController(
    private val managementsUseCase: ManagementsUseCase
) {
    @PostMapping("/keys/refresh")
    fun refreshKey(
        @RequestBody request: ManagementsUseCase.RefreshKeyRequest
    ): BackofficeResponse<RefreshKeyDto> {
        val response = RefreshKeyDto.of(managementsUseCase.refreshKey(request))
        return BackofficeResponse.ok(response)
    }
}
