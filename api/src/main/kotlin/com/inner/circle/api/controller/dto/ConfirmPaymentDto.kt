package com.inner.circle.api.controller.dto

import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import java.math.BigDecimal

data class ConfirmPaymentDto(
    val paymentKey: String?,
    val orderId: String?,
    val cardNumber: String?,
    val amount: BigDecimal?
) {
    companion object {
        fun of(confirmPaymentCore: ConfirmPaymentCoreDto): ConfirmPaymentDto =
            ConfirmPaymentDto(
                paymentKey = confirmPaymentCore.paymentKey,
                orderId = confirmPaymentCore.orderId,
                cardNumber = confirmPaymentCore.cardNumber,
                amount = confirmPaymentCore.amount
            )
    }
}
