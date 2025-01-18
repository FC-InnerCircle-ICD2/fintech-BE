package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.CreateOrUpdateKeyDto
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
    @PostMapping("/keys")
    fun createOrUpdateKey(
        @RequestBody request: ManagementsUseCase.CreateOrUpdateKeyRequest
    ): BackofficeResponse<CreateOrUpdateKeyDto> {
        val response = CreateOrUpdateKeyDto.of(managementsUseCase.createOrUpdateKey(request))
        return BackofficeResponse.ok(response)
    }
}
