package com.inner.circle.api.controller.dto

import com.inner.circle.core.domain.PaymentType as CorePaymentType

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: CorePaymentType): PaymentType =
            when (paymentType) {
                CorePaymentType.CARD -> CARD
            }
    }
}
