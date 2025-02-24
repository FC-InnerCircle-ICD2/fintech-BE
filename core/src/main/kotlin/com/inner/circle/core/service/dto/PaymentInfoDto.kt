package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentInfo
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentInfoDto(
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
) {
    companion object {
        fun of(paymentInfo: PaymentInfo): PaymentInfoDto =
            PaymentInfoDto(
                orderId = paymentInfo.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = PaymentProcessStatus.valueOf(paymentInfo.orderStatus.name),
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                merchantName = paymentInfo.merchantName,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                requestTime = paymentInfo.requestTime,
                cardNumber = paymentInfo.cardNumber,
                expirationPeriod = paymentInfo.expirationPeriod,
                cvc = paymentInfo.cvc,
                cardCompany = paymentInfo.cardCompany
            )
    }
}
