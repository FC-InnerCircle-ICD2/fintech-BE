package com.inner.circle.infra.sse

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import org.slf4j.LoggerFactory

class SseConnectionPool private constructor() {
    companion object {
        private val log = LoggerFactory.getLogger(SseConnectionPool::class.java)
        private val connectionPool: MutableMap<String, MutableList<SseConnection>> =
            ConcurrentHashMap()

        fun addConnection(connection: SseConnection): SseConnection {
            val sseConnections =
                connectionPool.computeIfAbsent(connection.uniqueKey) { CopyOnWriteArrayList() }
            sseConnections.add(connection)
            return connection
        }

        fun getConnections(uniqueKey: String): List<SseConnection> {
            val sseConnections: List<SseConnection> = (
                connectionPool[uniqueKey]
                    ?: throw NoSuchElementException("Session not found for key: $uniqueKey")
            )
            return Collections.unmodifiableList(sseConnections)
        }

        fun removeConnections(uniqueKey: String) {
            log.error("SseConnectionPool.removeConnections: $uniqueKey")
            connectionPool.remove(uniqueKey)
        }
    }
}
