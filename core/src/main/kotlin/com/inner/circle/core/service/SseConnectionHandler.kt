package com.inner.circle.core.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.core.service.dto.SseConnectionDto
import com.inner.circle.core.usecase.SseConnectionUseCase
import com.inner.circle.infra.sse.SseConnection
import com.inner.circle.infra.sse.SseConnectionPool
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class SseConnectionHandler(
    private val objectMapper: ObjectMapper
) : SseConnectionUseCase {
    private val log = LoggerFactory.getLogger(SseConnectionHandler::class.java)

    override fun connect(request: SseConnectionUseCase.ConnectRequest): SseConnectionDto {
        val connection =
            SseConnectionPool.addConnection(
                connection =
                    SseConnection(
                        request.uniqueKey,
                        request.userType,
                        SseEmitter(600 * 1000L)
                    )
            )
        return SseConnectionDto(
            uniqueKey = connection.uniqueKey,
            userType = connection.userType,
            sseEmitter = connection.sseEmitter
        )
    }

    override fun sendMessage(request: SseConnectionUseCase.SendMessageRequest) {
        try {
            SseConnectionPool
                .getConnections(uniqueKey = request.uniqueKey)
                .forEach { connection ->
                    connection.sendMessage(
                        eventName = request.eventName,
                        data = objectMapper.writeValueAsString(request.message)
                    )
                }
        } catch (e: JsonProcessingException) {
            log.error(
                "Failed to serialize message. " +
                    "uniqueKey: ${request.uniqueKey}, message: ${request.message}",
                e
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun close(uniqueKey: String) {
        SseConnectionPool.removeConnections(uniqueKey = uniqueKey)
    }
}
