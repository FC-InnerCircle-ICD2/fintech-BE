package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentInfo
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentInfoDto(
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
) {
    companion object {
        fun of(paymentInfo: PaymentInfo): PaymentInfoDto =
            PaymentInfoDto(
                orderId = paymentInfo.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = paymentInfo.orderStatus,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber = paymentInfo.cardNumber,
                expirationPeriod = paymentInfo.expirationPeriod,
                cvc = paymentInfo.cvc
            )
    }
}
