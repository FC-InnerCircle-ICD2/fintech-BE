package com.inner.circle.apibackoffice.controller.dto

enum class PaymentType {
    NORMAL,
    BRAND_PAY,
    BILLING,

    // TODO: paymeny쪽과 협의 후 정리 필요 정합성을 맞추기 위해 임시 추가
    CARD;

    companion object {
        fun of(paymentType: com.inner.circle.corebackoffice.domain.PaymentType): PaymentType =
            when (paymentType) {
                com.inner.circle.corebackoffice.domain.PaymentType.NORMAL -> NORMAL
                com.inner.circle.corebackoffice.domain.PaymentType.BRAND_PAY -> BRAND_PAY
                com.inner.circle.corebackoffice.domain.PaymentType.BILLING -> BILLING
                com.inner.circle.corebackoffice.domain.PaymentType.CARD -> CARD
            }
    }
}
