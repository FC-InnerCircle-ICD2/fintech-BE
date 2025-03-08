package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.SseConnectionDto

interface SseConnectionUseCase {
    data class ConnectRequest(
        val uniqueKey: String,
        val userType: String
    )

    data class SendMessageRequest(
        val uniqueKey: String,
        val eventName: String,
        val message: Any
    )

    fun connect(request: ConnectRequest): SseConnectionDto

    fun sendMessage(request: SendMessageRequest)

    fun close(uniqueKey: String)
}
