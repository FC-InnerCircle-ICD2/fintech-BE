package com.inner.circle.infra.port

import java.math.BigDecimal
import java.time.LocalDateTime

fun interface SavePaymentRequestPort {
    data class Request(
        val orderId: String,
        val orderName: String?,
        val orderStatus: String?,
        val accountId: Long?,
        val merchantId: String,
        val paymentKey: String,
        val amount: BigDecimal,
        val cardNumber: String,
        val paymentType: String,
        val requestTime: LocalDateTime
    )

    fun save(request: Request)
}
