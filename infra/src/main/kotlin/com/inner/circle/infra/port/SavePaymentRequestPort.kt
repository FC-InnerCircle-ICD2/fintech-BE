package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.repository.entity.PaymentType
import java.math.BigDecimal
import java.time.LocalDateTime

fun interface SavePaymentRequestPort {
    data class Request(
        val orderId: String,
        val orderName: String?,
        val orderStatus: PaymentProcessStatus,
        val accountId: Long?,
        val merchantId: Long,
        val paymentKey: String?,
        val amount: BigDecimal,
        val cardNumber: String?,
        val paymentType: PaymentType,
        val requestTime: LocalDateTime,
        val merchantName: String
    )

    fun save(request: Request)
}
