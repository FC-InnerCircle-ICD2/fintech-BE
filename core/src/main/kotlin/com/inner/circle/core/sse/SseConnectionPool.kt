package com.inner.circle.core.sse

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SseConnectionPool : ConnectionPool<String, SseConnection> {
    private val log = LoggerFactory.getLogger(SseConnectionPool::class.java)

    companion object {
        private val connectionPool: MutableMap<String, MutableList<SseConnection>> =
            ConcurrentHashMap()
    }

    override fun addSession(
        uniqueKey: String,
        session: SseConnection
    ) {
        val sseConnections = connectionPool.getOrDefault(uniqueKey, CopyOnWriteArrayList())
        sseConnections.add(session)
        connectionPool[uniqueKey] = sseConnections
    }

    override fun getSessions(uniqueKey: String): List<SseConnection> {
        val sseConnections: List<SseConnection> = (
            connectionPool[uniqueKey]
                ?: throw NoSuchElementException("Session not found for key: $uniqueKey")
        )
        return Collections.unmodifiableList(sseConnections)
    }

    override fun removeSession(
        uniqueKey: String,
        connectionKey: String
    ) {
        val sseConnections = connectionPool[uniqueKey]
        if (!sseConnections.isNullOrEmpty()) {
            connectionPool[uniqueKey] =
                sseConnections
                    .stream()
                    .filter { connection -> connection.connectionKey != connectionKey }
                    .toList()
        }
    }

    override fun removeAllSessions(uniqueKey: String) {
        log.error("SseConnectionPool.removeSessions: $uniqueKey")
        connectionPool.remove(uniqueKey)
    }
}
