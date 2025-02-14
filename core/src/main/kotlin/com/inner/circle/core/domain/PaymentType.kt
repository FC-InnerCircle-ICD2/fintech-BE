package com.inner.circle.core.domain

import com.inner.circle.infra.repository.entity.PaymentType as InfraPaymentType

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: InfraPaymentType): PaymentType =
            when (paymentType) {
                InfraPaymentType.CARD -> CARD
            }
    }
}
