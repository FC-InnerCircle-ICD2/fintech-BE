package com.inner.circle.infra.structure.adaptor.dto

import com.inner.circle.infra.structure.adaptor.PaymentProcessStatus
import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentRequestDto(
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime
) {
    fun toEntity(): PaymentRequestEntity =
        PaymentRequestEntity(
            orderId = orderId,
            orderName = orderName,
            orderStatus = orderStatus.name,
            userId = null,
            merchantId = merchantId,
            paymentKey = paymentKey,
            amount = amount,
            requestTime = requestTime
        )

    companion object {
        fun of(
            orderId: String,
            orderName: String,
            status: String,
            merchantId: String,
            paymentKey: String?,
            amount: BigDecimal,
            requestTime: LocalDateTime
        ): PaymentRequestDto =
            PaymentRequestDto(
                orderId = orderId,
                orderName = orderName,
                orderStatus = PaymentProcessStatus.valueOf(status),
                merchantId = merchantId,
                paymentKey = paymentKey,
                amount = amount,
                requestTime = requestTime
            )

        fun fromEntity(paymentRequestEntity: PaymentRequestEntity): PaymentRequestDto =
            PaymentRequestDto(
                orderId = paymentRequestEntity.orderId,
                orderName = paymentRequestEntity.orderName,
                orderStatus = PaymentProcessStatus.valueOf(paymentRequestEntity.orderStatus),
                merchantId = paymentRequestEntity.merchantId,
                paymentKey = paymentRequestEntity.paymentKey,
                amount = paymentRequestEntity.amount,
                requestTime = paymentRequestEntity.requestTime
            )
    }
}
