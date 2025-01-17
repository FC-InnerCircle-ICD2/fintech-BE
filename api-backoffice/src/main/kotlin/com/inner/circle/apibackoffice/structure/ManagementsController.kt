package com.inner.circle.apibackoffice.structure

import com.inner.circle.corebackoffice.structure.usecase.ManagementsUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managements")
class ManagementsController(
    private val managementsUseCase: ManagementsUseCase
) {
    @PostMapping("/keys/refresh")
    fun refreshKey(
        @RequestBody request: ManagementsUseCase.RefreshKeyRequest
    ): ResponseEntity<String> {
        managementsUseCase.refreshKey(request)
        return ResponseEntity.ok().build()
    }
}
