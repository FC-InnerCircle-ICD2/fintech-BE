package com.inner.circle.apibackoffice.controller.dto

import com.inner.circle.corebackoffice.domain.PaymentType as CoreBackofficePaymentType

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: CoreBackofficePaymentType): PaymentType =
            when (paymentType) {
                CoreBackofficePaymentType.CARD -> CARD
            }
    }
}
