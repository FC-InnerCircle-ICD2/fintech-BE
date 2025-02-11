package com.inner.circle.corebackoffice.domain

import com.inner.circle.infrabackoffice.repository.entity.PaymentType as InfraBackOfficePaymentType

enum class PaymentType {
    CARD;

    companion object {
        fun of(paymentType: InfraBackOfficePaymentType): PaymentType =
            when (paymentType) {
                InfraBackOfficePaymentType.CARD -> CARD
            }
    }
}
