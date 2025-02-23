package com.inner.circle.core.domain

import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentInfo(
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val accountId: Long?,
    val merchantId: Long,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String,
    val cardCompany: String,
    val merchantName: String
)
