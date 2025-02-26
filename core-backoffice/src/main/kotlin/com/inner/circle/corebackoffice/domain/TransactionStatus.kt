package com.inner.circle.corebackoffice.domain

import com.inner.circle.infrabackoffice.repository.entity.TransactionStatus as InfraTransactionStatus

enum class TransactionStatus {
    APPROVED,
    CANCELED;

    companion object {
        fun of(transactionStatus: InfraTransactionStatus): TransactionStatus =
            when (transactionStatus) {
                InfraTransactionStatus.APPROVED -> APPROVED
                InfraTransactionStatus.CANCELED -> CANCELED
            }
    }
}

fun TransactionStatus.convertInfraTransactionStatus(): InfraTransactionStatus =
    when (this) {
        TransactionStatus.APPROVED -> InfraTransactionStatus.APPROVED
        TransactionStatus.CANCELED -> InfraTransactionStatus.CANCELED
    }
