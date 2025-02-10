package com.inner.circle.corebackoffice.domain

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED;

    companion object {
        fun of(
            transactionStatus: com.inner.circle.infrabackoffice.repository.entity.TransactionStatus
        ): TransactionStatus =
            when (transactionStatus) {
                com.inner.circle.infrabackoffice.repository.entity
                    .TransactionStatus.APPROVED -> APPROVED
                com.inner.circle.infrabackoffice.repository.entity
                    .TransactionStatus.CANCELED -> CANCELED
                com.inner.circle.infrabackoffice.repository.entity
                    .TransactionStatus.REFUNDED -> REFUNDED
            }
    }
}
