package com.inner.circle.core.sse

interface ConnectionPool<T, R> {
    fun addSession(
        uniqueKey: T,
        session: R
    )

    fun getSessions(uniqueKey: T): List<R>

    fun removeSession(
        uniqueKey: T,
        connectionKey: String
    )

    fun removeAllSessions(uniqueKey: T)
}
