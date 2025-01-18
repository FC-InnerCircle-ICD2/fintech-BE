package com.inner.circle.api.sse.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.sse.connection.SseConnection
import com.inner.circle.api.sse.connection.SseConnectionPool
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @GetMapping(path = ["/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @Parameter(hidden = true)
        // 세션 객체로 변경 예정
        // @AuthenticationPrincipal 스프링security 삭제
        @RequestBody session: String
    ): ResponseBodyEmitter {
        log.info("login user {}", session)

        val sseConnection =
            SseConnection.connect(
                session,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }

    @GetMapping("/push-event")
    fun pushEvent(
        @Parameter(hidden = true)
        // 세션 객체로 변경 예정
        // @AuthenticationPrincipal 스프링security 삭제
        @RequestBody session: String
    ) {
        val sseConnection = sseConnectionPool.getSession(session)

        sseConnection?.let {
            it.sendMessage("hello world")
        }
    }
}
