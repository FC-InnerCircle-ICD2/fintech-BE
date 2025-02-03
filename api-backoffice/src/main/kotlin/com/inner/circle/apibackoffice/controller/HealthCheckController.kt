package com.inner.circle.apibackoffice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HealthCheckController {
    @GetMapping("/health-check")
    fun check(): ResponseEntity<String> = ResponseEntity.ok("ok")
}
