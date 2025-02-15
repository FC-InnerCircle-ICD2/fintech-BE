package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.ConfirmPayment
import java.math.BigDecimal

data class ConfirmPaymentCoreDto(
    val paymentKey: String,
    val merchantId: Long,
    val orderId: String,
    val cardNumber: String,
    val amount: BigDecimal
) {
    companion object {
        fun of(confirmPayment: ConfirmPayment): ConfirmPaymentCoreDto =
            ConfirmPaymentCoreDto(
                paymentKey = confirmPayment.paymentKey,
                merchantId = confirmPayment.merchantId,
                orderId = confirmPayment.orderId,
                cardNumber = confirmPayment.cardNumber,
                amount = confirmPayment.amount
            )
    }
}
