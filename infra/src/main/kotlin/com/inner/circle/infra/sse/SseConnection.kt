package com.inner.circle.infra.sse

import java.io.IOException
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class SseConnection(
    val uniqueKey: String,
    val userType: String,
    val sseEmitter: SseEmitter
) {
    init {
        sseEmitter.onCompletion {
            // oncomplete 메시지
            sendMessage("oncomplete", "disconnect")
        }

        // on timeout
        sseEmitter.onTimeout {
            sseEmitter.complete()
        }

        // onopen 메시지
        sendMessage("onopen", "connect")
    }

    fun sendMessage(
        eventName: String,
        data: String
    ) {
        try {
            val event =
                SseEmitter
                    .event()
                    .name(eventName)
                    .data(data)

            sseEmitter.send(event)
        } catch (e: IOException) {
            sseEmitter.completeWithError(e)
        }
    }
}
