package com.inner.circle.api.payment.controller.dto

import com.inner.circle.core.structure.service.dto.MobilePaymentCoreDto
import java.math.BigDecimal

data class MobilePaymentDto(
    val paymentKey: String?,
    val orderId: String?,
    val cardNumber: String?,
    val amount: BigDecimal?,
    val errorCode: String?,
    val errorMessage: String?,
    val successYn: Boolean
) {
    companion object {
        fun of(mobilePaymentCore: MobilePaymentCoreDto): MobilePaymentDto =
            MobilePaymentDto(
                paymentKey = mobilePaymentCore.paymentKey,
                orderId = mobilePaymentCore.orderId,
                cardNumber = mobilePaymentCore.cardNumber,
                amount = mobilePaymentCore.amount,
                errorCode = mobilePaymentCore.errorCode,
                errorMessage = mobilePaymentCore.errorMessage,
                successYn = mobilePaymentCore.successYn
            )
    }
}
