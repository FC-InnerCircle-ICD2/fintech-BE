package com.inner.circle.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.core.structure.sse.SseConnection
import com.inner.circle.core.structure.sse.SseConnectionPool
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter

@RestController
@RequestMapping("/api/sse")
class SseApiController(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private val log = LoggerFactory.getLogger(SseApiController::class.java)
    }

    @GetMapping(path = ["/connect/{order_id}"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @Parameter(hidden = true)
        @PathVariable("order_id") orderId: String
    ): ResponseBodyEmitter {
        log.info("login user {}", orderId)

        val sseConnection =
            SseConnection.connect(
                orderId,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }

    @GetMapping("/push-event/{order_id}")
    fun pushEvent(
        @Parameter(hidden = false)
        @PathVariable("order_id") orderId: String
    ) {
        val sseConnection = sseConnectionPool.getSession(orderId)

        sseConnection.sendMessage("hello world")
    }
}
