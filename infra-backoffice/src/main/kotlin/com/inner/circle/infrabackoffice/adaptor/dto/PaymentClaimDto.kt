package com.inner.circle.infrabackoffice.adaptor.dto

import com.inner.circle.exception.PaymentClaimException
import com.inner.circle.infrabackoffice.repository.entity.PaymentType
import java.math.BigDecimal

class PaymentClaimDto(
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val paymentType: PaymentType?,
    val cardNumber: String?,
    val merchantId: Long,
    val paymentKey: String?,
    val amount: BigDecimal,
    val merchantName: String = "testMerchant"
) {
    init {
        validateRequiredOrderInformation(orderName, merchantId, orderId)
        validateOrderStatus(orderStatus)
        validateAmount(orderStatus, amount, orderId)
    }

    companion object {
        private fun validateAmount(
            orderStatus: PaymentProcessStatus,
            amount: BigDecimal,
            orderId: String
        ) {
            if (orderStatus == PaymentProcessStatus.READY && amount < BigDecimal.ZERO) {
                throw PaymentClaimException.InvalidClaimAmountException(orderId)
            }
        }

        private fun validateRequiredOrderInformation(
            orderName: String?,
            merchantId: Long,
            orderId: String?
        ) {
            if (orderName.isNullOrEmpty() ||
                orderId.isNullOrEmpty() ||
                merchantId <= 0L
            ) {
                throw PaymentClaimException.BadPaymentClaimRequestException()
            }
        }

        private fun validateOrderStatus(orderStatus: PaymentProcessStatus?) {
            if (orderStatus === null || orderStatus !== PaymentProcessStatus.READY) {
                throw PaymentClaimException.BadPaymentClaimRequestException()
            }
        }
    }
}
