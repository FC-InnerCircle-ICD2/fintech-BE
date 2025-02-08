package com.inner.circle.api.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.controller.PaymentV1Api
import com.inner.circle.core.sse.SseConnectionPool
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter

@Tag(name = "SSE - user", description = "결제 고객(App) SSE API")
@PaymentV1Api
class UserSseApiController(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    companion object {
        private val log = LoggerFactory.getLogger(UserSseApiController::class.java)
    }

    @GetMapping(path = ["/user/sse/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @Parameter(hidden = false)
        @RequestParam userId: String,
        @RequestParam orderId: String
    ): ResponseBodyEmitter {
        log.info("SSE user {}", userId + "_" + orderId)

        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                userId + "_" + orderId,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }

    @GetMapping("/user/sse/push-event")
    fun pushEvent(
        @Parameter(hidden = false)
        @RequestParam userId: String,
        @RequestParam orderId: String,
        @RequestParam message: String
    ) {
        val sseConnection =
            sseConnectionPool.getSession(
                userId + "_" + orderId
            )

        sseConnection.sendMessage(message)
    }
}
