package com.inner.circle.api.controller.dto

import com.inner.circle.core.domain.PaymentProcessStatus
import com.inner.circle.core.service.dto.CancelPaymentDto as CoreCancelPaymentDto

data class CancelPaymentDto(
    val orderId: String,
    val merchantId: Long,
    val accountId: Long?,
    val orderStatus: PaymentProcessStatus
) {
    companion object {
        fun of(coreCancelPaymentDto: CoreCancelPaymentDto): CancelPaymentDto =
            CancelPaymentDto(
                orderId = coreCancelPaymentDto.orderId,
                merchantId = coreCancelPaymentDto.merchantId,
                accountId = coreCancelPaymentDto.accountId,
                orderStatus = PaymentProcessStatus.valueOf(coreCancelPaymentDto.orderStatus.name)
            )
    }
}
