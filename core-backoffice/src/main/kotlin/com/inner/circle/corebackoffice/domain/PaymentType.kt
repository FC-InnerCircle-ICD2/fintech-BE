package com.inner.circle.corebackoffice.domain

enum class PaymentType {
    NORMAL,
    BRAND_PAY,
    BILLING,

    // TODO: paymeny쪽과 협의 후 정리 필요 정합성을 맞추기 위해 임시 추가
    CARD;

    companion object {
        fun of(
            paymentType: com.inner.circle.infrabackoffice.repository.entity.PaymentType
        ): PaymentType =
            when (paymentType) {
                com.inner.circle.infrabackoffice.repository.entity
                    .PaymentType.NORMAL -> NORMAL
                com.inner.circle.infrabackoffice.repository.entity
                    .PaymentType.BRAND_PAY -> BRAND_PAY
                com.inner.circle.infrabackoffice.repository.entity
                    .PaymentType.BILLING -> BILLING
                com.inner.circle.infrabackoffice.repository.entity
                    .PaymentType.CARD -> CARD
            }
    }
}
