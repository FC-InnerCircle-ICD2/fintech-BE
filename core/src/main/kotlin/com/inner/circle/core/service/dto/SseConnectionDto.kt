package com.inner.circle.core.service.dto

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class SseConnectionDto(
    val uniqueKey: String,
    val userType: String,
    val sseEmitter: SseEmitter
)
