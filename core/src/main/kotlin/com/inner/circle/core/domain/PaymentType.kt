package com.inner.circle.core.domain

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: com.inner.circle.infra.repository.entity.PaymentType): PaymentType =
            when (paymentType) {
                com.inner.circle.infra.repository.entity.PaymentType.CARD -> CARD
            }
    }
}
