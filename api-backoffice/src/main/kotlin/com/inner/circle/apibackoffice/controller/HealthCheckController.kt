package com.inner.circle.apibackoffice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@BackofficeV1Api
class HealthCheckController {
    @GetMapping("/health-check")
    fun check(
        @RequestBody message: String
    ): String = message
}
