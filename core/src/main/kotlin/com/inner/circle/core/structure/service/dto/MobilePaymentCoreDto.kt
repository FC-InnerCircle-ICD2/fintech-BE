package com.inner.circle.core.structure.service.dto

import com.inner.circle.core.structure.domain.MobilePayment
import java.math.BigDecimal

data class MobilePaymentCoreDto(
    val paymentKey: String?,
    val orderId: String?,
    val cardNumber: String?,
    val amount: BigDecimal?,
    val errorCode: String?,
    val errorMessage: String?,
    val successYn: Boolean
) {
    companion object {
        fun of(mobilePayment: MobilePayment): MobilePaymentCoreDto =
            MobilePaymentCoreDto(
                paymentKey = mobilePayment.paymentKey,
                orderId = mobilePayment.orderId,
                cardNumber = mobilePayment.cardNumber,
                amount = mobilePayment.amount,
                errorCode = mobilePayment.errorCode,
                errorMessage = mobilePayment.errorMessage,
                successYn = mobilePayment.successYn
            )
    }
}
