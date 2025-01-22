package com.inner.circle.infra.port

import java.math.BigDecimal
import java.time.LocalDateTime

fun interface SavePaymentRequestPort {
    data class Request(
        val orderId: String,
        val orderName: String?,
        val orderStatus: String?,
        val userId: Long?,
        val merchantId: String,
        val paymentKey: String?,
        val amount: BigDecimal,
        val requestTime: LocalDateTime
    )

    fun save(request: SavePaymentRequestPort.Request)
}
