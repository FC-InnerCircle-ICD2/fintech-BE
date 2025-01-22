package com.inner.circle.api.healthcheck.controller

import com.inner.circle.api.controller.PaymentV1Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@PaymentV1Api
class HealthCheckController {
    @GetMapping("/health-check")
    fun check(
        @RequestBody message: String
    ): String = message
}
