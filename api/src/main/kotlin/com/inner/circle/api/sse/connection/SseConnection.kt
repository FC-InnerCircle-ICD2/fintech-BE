package com.inner.circle.api.sse.connection

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

data class SseConnection(
    val uniqueKey: String,
    private val connectionPool: ConnectionPool<String, SseConnection>,
    private val objectMapper: ObjectMapper
) {

    val sseEmitter: SseEmitter = SseEmitter(600 * 1000L)

    init {
        // on completion
        sseEmitter.onCompletion {
            connectionPool.onCompletionCallback(this)
        }

        // on timeout
        sseEmitter.onTimeout {
            sseEmitter.complete()
        }

        // onopen 메시지
        sendMessage("onopen", "connect")
    }

    companion object {
        fun connect(
            uniqueKey: String,
            connectionPool: ConnectionPool<String, SseConnection>,
            objectMapper: ObjectMapper
        ): SseConnection {
            return SseConnection(uniqueKey, connectionPool, objectMapper)
        }
    }

    fun sendMessage(eventName: String, data: Any) {
        try {
            val json = objectMapper.writeValueAsString(data)
            val event = SseEmitter.event()
                .name(eventName)
                .data(json)

            sseEmitter.send(event)
        } catch (e: IOException) {
            sseEmitter.completeWithError(e)
        }
    }

    fun sendMessage(data: Any) {
        try {
            val json = objectMapper.writeValueAsString(data)
            val event = SseEmitter.event()
                .data(json)

            sseEmitter.send(event)
        } catch (e: IOException) {
            sseEmitter.completeWithError(e)
        }
    }
}
