package com.inner.circle.infra.structure.port

import kotlinx.datetime.LocalDateTime

fun interface SavePaymentPort {
    data class Request(
        val userName: String,
        val amount: Long,
        val requestAt: LocalDateTime
    )

    fun save(request: Request)
}
