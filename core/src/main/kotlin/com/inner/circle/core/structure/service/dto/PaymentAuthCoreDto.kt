package com.inner.circle.core.structure.service.dto

import com.inner.circle.core.structure.domain.PaymentAuth

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
