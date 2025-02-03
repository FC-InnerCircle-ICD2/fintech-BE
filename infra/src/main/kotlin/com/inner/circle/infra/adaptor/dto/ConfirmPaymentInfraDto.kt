package com.inner.circle.infra.adaptor.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ConfirmPaymentInfraDto(
    val orderId: String,
    val orderName: String?,
    val orderStatus: String?,
    val accountId: Long?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String
)
