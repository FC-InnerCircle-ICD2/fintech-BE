package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentAuth

data class PaymentAuthCoreDto(
    val cardNumber: String,
    val isValid: Boolean
) {
    companion object {
        fun of(paymentAuth: PaymentAuth): PaymentAuthCoreDto =
            PaymentAuthCoreDto(
                cardNumber = paymentAuth.cardNumber,
                isValid = paymentAuth.isValid
            )
    }
}
