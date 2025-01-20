package com.inner.circle.infra.structure.adaptor.dto

import com.inner.circle.infra.structure.adaptor.PaymentProcessStatus
import com.inner.circle.infra.structure.repository.entity.PaymentRequestEntity
import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentRequestDto(
    val paymentRequestId: String?,
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
    fun toInitGenerate(): PaymentRequestEntity =
        PaymentRequestEntity(
            id = ULID().nextULID(),
            orderId = orderId,
            orderName = orderName,
            orderStatus = orderStatus.name,
            userId = null,
            merchantId = merchantId,
            paymentKey = paymentKey,
            amount = amount,
            successUrl = successUrl,
            failUrl = failUrl,
            paymentToken = paymentToken,
            requestTime = requestTime
        )

    companion object {
        fun fromEntity(paymentRequestEntity: PaymentRequestEntity): PaymentRequestDto =
            PaymentRequestDto(
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
}
