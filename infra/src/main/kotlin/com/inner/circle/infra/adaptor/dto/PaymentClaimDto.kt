package com.inner.circle.infra.adaptor.dto

import com.google.common.hash.Hashing
import com.inner.circle.exception.PaymentClaimException
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import com.inner.circle.infra.repository.entity.PaymentStatusType
import com.inner.circle.infra.repository.entity.PaymentType
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

class PaymentClaimDto(
    val paymentRequestId: Long?,
    val orderId: String,
    val orderName: String?,
    val orderStatus: PaymentProcessStatus,
    val paymentType: PaymentType?,
    val cardNumber: String?,
    val merchantId: Long,
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
                orderStatus = PaymentProcessStatus.valueOf(paymentRequestEntity.orderStatus.name),
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

    fun toInitGenerate(tokenData: PaymentTokenDto): PaymentRequestEntity =
        PaymentRequestEntity(
            id = null,
            orderId = orderId,
            orderName = orderName,
            orderStatus = PaymentStatusType.valueOf(orderStatus.name),
            accountId = null,
            merchantId = merchantId,
            merchantName = merchantName,
            paymentType = paymentType ?: PaymentType.CARD,
            cardNumber = cardNumber,
            paymentKey = paymentKey,
            amount = amount,
            paymentToken =
                Hashing
                    .murmur3_128()
                    .hashString(
                        tokenData.generatedToken,
                        StandardCharsets.UTF_8
                    ).toString(),
            requestTime = requestTime
        )
}
