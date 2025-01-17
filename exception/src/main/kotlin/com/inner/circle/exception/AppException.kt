package com.inner.circle.exception

open class AppException(
    val status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
