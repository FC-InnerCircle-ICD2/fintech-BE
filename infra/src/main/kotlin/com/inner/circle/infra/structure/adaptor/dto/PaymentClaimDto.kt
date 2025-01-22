package com.inner.circle.infra.structure.adaptor.dto

import com.inner.circle.infra.structure.adaptor.enum.PaymentProcessStatus
import com.inner.circle.infra.structure.repository.entity.PaymentClaimEntity
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentClaimDto(
    val paymentRequestId: Long?,
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val successUrl: String,
    val failUrl: String,
    val paymentToken: String?
) {
    companion object {
        fun fromEntity(paymentRequestEntity: PaymentClaimEntity): PaymentClaimDto =
            PaymentClaimDto(
                paymentRequestId = paymentRequestEntity.id,
                orderId = paymentRequestEntity.orderId,
                orderName = paymentRequestEntity.orderName,
                orderStatus = PaymentProcessStatus.valueOf(paymentRequestEntity.orderStatus),
                merchantId = paymentRequestEntity.merchantId,
                paymentKey = paymentRequestEntity.paymentKey,
                amount = paymentRequestEntity.amount,
                requestTime = paymentRequestEntity.requestTime,
                successUrl = paymentRequestEntity.successUrl,
                failUrl = paymentRequestEntity.failUrl,
                paymentToken = paymentRequestEntity.paymentToken
            )
    }

    fun toInitGenerate(tokenData: PaymentTokenDto): PaymentClaimEntity =
        PaymentClaimEntity(
            id = null,
            orderId = orderId,
            orderName = orderName,
            orderStatus = orderStatus.name,
            userId = null,
            merchantId = merchantId,
            paymentKey = paymentKey,
            amount = amount,
            successUrl = successUrl,
            failUrl = failUrl,
            paymentToken = tokenData.generatedToken,
            requestTime = requestTime
        )
}
