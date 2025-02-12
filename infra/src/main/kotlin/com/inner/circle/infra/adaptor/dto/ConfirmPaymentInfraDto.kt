package com.inner.circle.infra.adaptor.dto

import com.inner.circle.infra.repository.entity.PaymentStatusType
import java.math.BigDecimal
import java.time.LocalDateTime

data class ConfirmPaymentInfraDto(
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentStatusType,
    val accountId: Long?,
    val merchantId: Long,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val cardNumber: String?,
    val expirationPeriod: String?,
    val cvc: String?,
    val merchantName: String
)
