package com.inner.circle.core.structure.sse

import java.util.concurrent.ConcurrentHashMap
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SseConnectionPool : ConnectionPool<String, SseConnection> {
    companion object {
        private val connectionPool: MutableMap<String, SseConnection> = ConcurrentHashMap()
        private val log = LoggerFactory.getLogger(SseConnectionPool::class.java)
    }

    override fun addSession(
        uniqueKey: String,
        sseConnection: SseConnection
    ) {
        connectionPool[uniqueKey] = sseConnection
    }

    override fun getSession(uniqueKey: String): SseConnection =
        connectionPool[uniqueKey]
            ?: throw NoSuchElementException("Session not found for key: $uniqueKey")

    override fun onCompletionCallback(session: SseConnection) {
        log.info("call back connection pool completion : {}", session)
        connectionPool.remove(session.uniqueKey)
    }
}
