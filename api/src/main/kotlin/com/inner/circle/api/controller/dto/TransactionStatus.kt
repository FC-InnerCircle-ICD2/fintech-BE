package com.inner.circle.api.controller.dto

import com.inner.circle.core.domain.TransactionStatus as CoreTransactionStatus

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED;

    companion object {
        fun of(transactionStatus: CoreTransactionStatus): TransactionStatus =
            when (transactionStatus) {
                CoreTransactionStatus.APPROVED -> APPROVED
                CoreTransactionStatus.CANCELED -> CANCELED
                CoreTransactionStatus.REFUNDED -> REFUNDED
            }
    }
}

fun TransactionStatus.convertCoreTransactionStatus(): CoreTransactionStatus =
    when (this) {
        TransactionStatus.APPROVED -> CoreTransactionStatus.APPROVED
        TransactionStatus.CANCELED -> CoreTransactionStatus.CANCELED
        TransactionStatus.REFUNDED -> CoreTransactionStatus.REFUNDED
    }
