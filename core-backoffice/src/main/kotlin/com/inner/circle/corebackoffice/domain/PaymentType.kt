package com.inner.circle.corebackoffice.domain

enum class PaymentType {
    CARD;

    companion object {
        fun of(
            paymentType: com.inner.circle.infrabackoffice.repository.entity.PaymentType
        ): PaymentType =
            when (paymentType) {
                com.inner.circle.infrabackoffice.repository.entity
                    .PaymentType.CARD -> CARD
            }
    }
}
