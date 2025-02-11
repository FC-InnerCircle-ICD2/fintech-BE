package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentType
import java.math.BigDecimal

data class PaymentRequestDto(
    val orderId: String,
    val orderName: String?,
    val cardNumber: String,
    val orderStatus: String?,
    val accountId: Long?,
    val merchantId: String,
    val paymentKey: String,
    val amount: BigDecimal,
    val paymentType: PaymentType,
    val requestTime: java.time.LocalDateTime?
)
