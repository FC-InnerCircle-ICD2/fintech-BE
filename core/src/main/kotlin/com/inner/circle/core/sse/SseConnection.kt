package com.inner.circle.core.sse

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class SseConnection(
    val connectionKey: String,
    private val connectionPool: ConnectionPool<String, SseConnection>,
    private val objectMapper: ObjectMapper
) {
    val sseEmitter: SseEmitter = SseEmitter(600 * 1000L)

    init {
        // on timeout
        sseEmitter.onTimeout {
            sseEmitter.complete()
        }

        // onopen 메시지
        sendMessage("onopen", "connect")
    }

    companion object {
        fun connect(
            connectionKey: String,
            connectionPool: ConnectionPool<String, SseConnection>,
            objectMapper: ObjectMapper
        ): SseConnection =
            SseConnection(
                connectionKey = connectionKey,
                connectionPool = connectionPool,
                objectMapper = objectMapper
            )
    }

    fun sendMessage(
        eventName: String,
        data: Any
    ) {
        try {
            val json = objectMapper.writeValueAsString(data)
            val event =
                SseEmitter
                    .event()
                    .name(eventName)
                    .data(json)

            sseEmitter.send(event)
        } catch (e: IOException) {
            sseEmitter.completeWithError(e)
        }
    }
}
