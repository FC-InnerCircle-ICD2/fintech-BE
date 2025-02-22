package com.inner.circle.apibackoffice.controller.dto

import com.inner.circle.corebackoffice.domain.TransactionStatus as CoreTransactionStatus

enum class TransactionStatus {
    APPROVED,
    CANCELED,
    REFUNDED;

    companion object {
        fun of(
            transactionStatus: com.inner.circle.corebackoffice.domain.TransactionStatus
        ): TransactionStatus =
            when (transactionStatus) {
                com.inner.circle.corebackoffice.domain.TransactionStatus.APPROVED -> APPROVED
                com.inner.circle.corebackoffice.domain.TransactionStatus.CANCELED -> CANCELED
                com.inner.circle.corebackoffice.domain.TransactionStatus.REFUNDED -> REFUNDED
            }
    }
}

fun TransactionStatus.convertCoreTransactionStatus(): CoreTransactionStatus =
    when (this) {
        TransactionStatus.APPROVED -> CoreTransactionStatus.APPROVED
        TransactionStatus.CANCELED -> CoreTransactionStatus.CANCELED
        TransactionStatus.REFUNDED -> CoreTransactionStatus.REFUNDED
    }
