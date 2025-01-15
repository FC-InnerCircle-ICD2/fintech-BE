package com.inner.circle.api.sse.connection

interface ConnectionPool<T, R>{
    fun addSession(uniqueKey: T, session: R)
    fun getSession(uniqueKey: T): R
    fun onCompletionCallback(session: R)
}
