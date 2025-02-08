package com.inner.circle.apibackoffice.controller.dto

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED,

    // TODO: paymeny쪽과 협의 후 정리 필요 정합성을 맞추기 위해 임시 추가
    APPROVE;

    companion object {
        fun of(
            transactionStatus: com.inner.circle.corebackoffice.domain.TransactionStatus
        ): TransactionStatus =
            when (transactionStatus) {
                com.inner.circle.corebackoffice.domain.TransactionStatus.APPROVED -> APPROVED
                com.inner.circle.corebackoffice.domain.TransactionStatus.CANCELED -> CANCELED
                com.inner.circle.corebackoffice.domain.TransactionStatus.REFUNDED -> REFUNDED
                com.inner.circle.corebackoffice.domain.TransactionStatus.APPROVE -> APPROVE
            }
    }
}
