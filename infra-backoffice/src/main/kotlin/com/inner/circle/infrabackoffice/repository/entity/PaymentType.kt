package com.inner.circle.infrabackoffice.repository.entity

enum class PaymentType {
    NORMAL,
    BRAND_PAY,
    BILLING,

    // TODO: paymeny쪽과 협의 후 정리 필요 정합성을 맞추기 위해 임시 추가
    CARD
}
