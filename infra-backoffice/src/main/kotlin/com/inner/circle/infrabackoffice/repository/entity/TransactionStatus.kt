package com.inner.circle.infrabackoffice.repository.entity

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED,

    // TODO: paymeny쪽과 협의 후 정리 필요 정합성을 맞추기 위해 임시 추가
    APPROVE
}
