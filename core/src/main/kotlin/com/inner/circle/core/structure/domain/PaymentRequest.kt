package com.inner.circle.core.structure.domain

import com.inner.circle.core.structure.domain.enum.PaymentProcessStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentRequest(
    val orderId: String,
    val orderName: String,
    val orderStatus: PaymentProcessStatus,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime
) {
    companion object {
    }
}
