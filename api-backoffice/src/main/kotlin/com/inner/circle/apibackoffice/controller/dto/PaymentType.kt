package com.inner.circle.apibackoffice.controller.dto

enum class PaymentType {
    NORMAL,
    BRAND_PAY,
    BILLING;

    companion object {
        fun of(paymentType: com.inner.circle.corebackoffice.domain.PaymentType): PaymentType =
            when (paymentType) {
                com.inner.circle.corebackoffice.domain.PaymentType.NORMAL -> NORMAL
                com.inner.circle.corebackoffice.domain.PaymentType.BRAND_PAY -> BRAND_PAY
                com.inner.circle.corebackoffice.domain.PaymentType.BILLING -> BILLING
            }
    }
}
