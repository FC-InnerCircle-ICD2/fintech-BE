package com.inner.circle.core.domain

import com.inner.circle.infra.repository.entity.TransactionStatus as InfraTransactionStatus

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED;

    companion object {
        fun of(transactionStatus: InfraTransactionStatus): TransactionStatus =
            when (transactionStatus) {
                InfraTransactionStatus.APPROVED -> APPROVED
                InfraTransactionStatus.CANCELED -> CANCELED
                InfraTransactionStatus.REFUNDED -> REFUNDED
            }
    }
}
