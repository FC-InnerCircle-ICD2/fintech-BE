package com.inner.circle.infra.port

fun interface SavePaymentPort {
    data class Request(
        val userName: String,
        val amount: Long,
        val requestAt: kotlinx.datetime.LocalDateTime
    )

    fun save(request: Request)
}
