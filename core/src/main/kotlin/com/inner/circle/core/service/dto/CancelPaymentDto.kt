package com.inner.circle.core.service.dto

import com.inner.circle.core.domain.PaymentProcessStatus

data class CancelPaymentDto(
    val orderId: String,
    val merchantId: Long,
    val accountId: Long?,
    val orderStatus: PaymentProcessStatus
)
