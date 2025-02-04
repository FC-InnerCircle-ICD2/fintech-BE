package com.inner.circle.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.core.sse.SseConnectionPool
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter

@PaymentV1Api
class SseApiController(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private val log = LoggerFactory.getLogger(SseApiController::class.java)
    }

    @GetMapping(path = ["/sse/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ): ResponseBodyEmitter {
        log.info("SSE user {}", merchantId + "_" + orderId)

        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                merchantId + "_" + orderId,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }

    @GetMapping("/sse/push-event")
    fun pushEvent(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        val sseConnection =
            sseConnectionPool.getSession(
                merchantId + "_" + orderId
            )

        sseConnection.sendMessage("hello world")
    }
}
