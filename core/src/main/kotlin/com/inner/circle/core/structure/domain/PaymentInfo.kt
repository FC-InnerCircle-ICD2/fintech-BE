package com.inner.circle.core.structure.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentInfo (
    val orderId: String,
    val orderName: String?,
    val orderStatus: String?,
    val userId: Long?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String
)
