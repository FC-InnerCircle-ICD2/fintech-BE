package com.inner.circle.infra.adaptor.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class MobilePaymentInfraDto(
    val orderId: String,
    val orderName: String?,
    val orderStatus: String?,
    val userId: Long?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val cardNumber: String
)
