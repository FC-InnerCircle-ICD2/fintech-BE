package com.inner.circle.infra.adaptor.dto

import com.inner.circle.exception.PaymentClaimException
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import com.inner.circle.infra.repository.entity.PaymentType
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentClaimDto(
    val paymentRequestId: Long?,
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val paymentType: PaymentType?,
    val cardNumber: String?,
    val merchantId: String,
    val paymentKey: String?,
    val amount: BigDecimal,
    val requestTime: LocalDateTime,
    val merchantName: String = "testMerchant",
    val paymentToken: String?
) {
    init {
        validateRequiredOrderInformation(orderName, merchantId, orderId)
        validateOrderStatus(orderStatus)
        validateAmount(orderStatus, amount, orderId)
    }

    companion object {
        fun fromEntity(paymentRequestEntity: PaymentRequestEntity): PaymentClaimDto =
            PaymentClaimDto(
                paymentRequestId = paymentRequestEntity.id,
                orderId = paymentRequestEntity.orderId,
                orderName = paymentRequestEntity.orderName,
                orderStatus = PaymentProcessStatus.valueOf(paymentRequestEntity.orderStatus),
                merchantId = paymentRequestEntity.merchantId,
                merchantName = paymentRequestEntity.merchantName,
                paymentType = paymentRequestEntity.paymentType,
                cardNumber = paymentRequestEntity.cardNumber,
                paymentKey = paymentRequestEntity.paymentKey,
                amount = paymentRequestEntity.amount,
                requestTime = paymentRequestEntity.requestTime,
                paymentToken = paymentRequestEntity.paymentToken
            )

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
            merchantId: String,
            orderId: String?
        ) {
            if (orderName.isNullOrEmpty() ||
                merchantId.isNullOrEmpty() ||
                orderId.isNullOrEmpty()
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

    fun toInitGenerate(tokenData: PaymentTokenDto): PaymentRequestEntity =
        PaymentRequestEntity(
            id = null,
            orderId = orderId,
            orderName = orderName,
            orderStatus = orderStatus.name,
            accountId = null,
            merchantId = merchantId,
            merchantName = merchantName,
            paymentType = paymentType ?: PaymentType.CARD,
            cardNumber = cardNumber,
            paymentKey = paymentKey,
            amount = amount,
            paymentToken = tokenData.generatedToken,
            requestTime = requestTime
        )
}
