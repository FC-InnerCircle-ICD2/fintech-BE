package com.inner.circle.corebackoffice.domain

enum class PaymentType {
    NORMAL,
    BRAND_PAY,
    BILLING;

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
            }
    }
}
