package com.inner.circle.infra.adaptor.dto

import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentClaimDto(
    val paymentRequestId: Long?,
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val paymentType: String?,
    val cardNumber: String?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val paymentToken: String?
) {
    companion object {
        fun fromEntity(paymentRequestEntity: PaymentRequestEntity): PaymentClaimDto =
            PaymentClaimDto(
                paymentRequestId = paymentRequestEntity.id,
                orderId = paymentRequestEntity.orderId,
                orderName = paymentRequestEntity.orderName,
                orderStatus = PaymentProcessStatus.valueOf(paymentRequestEntity.orderStatus),
                merchantId = paymentRequestEntity.merchantId,
                paymentType = paymentRequestEntity.paymentType,
                cardNumber = paymentRequestEntity.cardNumber,
                paymentKey = paymentRequestEntity.paymentKey,
                amount = paymentRequestEntity.amount,
                requestTime = paymentRequestEntity.requestTime,
                paymentToken = paymentRequestEntity.paymentToken
            )
    }

    fun toInitGenerate(tokenData: PaymentTokenDto): PaymentRequestEntity =
        PaymentRequestEntity(
            id = null,
            orderId = orderId,
            orderName = orderName,
            orderStatus = orderStatus.name,
            userId = null,
            merchantId = merchantId,
            paymentType = paymentType ?: "CARD",
            cardNumber = cardNumber,
            paymentKey = paymentKey,
            amount = amount,
            paymentToken = tokenData.generatedToken,
            requestTime = requestTime
        )
}
