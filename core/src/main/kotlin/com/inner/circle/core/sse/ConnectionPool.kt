package com.inner.circle.core.sse

interface ConnectionPool<T, R> {
    fun addSession(
        uniqueKey: T,
        session: R
    )

    fun getSession(uniqueKey: T): R

    fun onCompletionCallback(session: R)
}
