package com.inner.circle.apibackoffice.controller.dto

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: com.inner.circle.corebackoffice.domain.PaymentType): PaymentType =
            when (paymentType) {
                com.inner.circle.corebackoffice.domain.PaymentType.CARD -> CARD
            }
    }
}
