package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.ConfirmPaymentInfraDto
import com.inner.circle.infra.port.ConfirmPaymentPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.UserCardRepository
import org.springframework.stereotype.Component

@Component
internal class ConfirmPaymentAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository,
    private val userCardRepository: UserCardRepository
) : ConfirmPaymentPort {
    override fun getCardNoAndPayInfo(request: ConfirmPaymentPort.Request): ConfirmPaymentInfraDto {
        val paymentRequest =
            paymentRequestRepository.findByOrderIdAndMerchantId(request.orderId, request.merchantId)
                ?: throw PaymentException.OrderNotFoundException(
                    request.orderId
                )
        return userCardRepository
            .findByUserId(paymentRequest.userId)
            ?.let {
                ConfirmPaymentInfraDto(
                    orderId = paymentRequest.orderId,
                    orderName = paymentRequest.orderName,
                    orderStatus = paymentRequest.orderStatus,
                    userId = paymentRequest.userId,
                    merchantId = paymentRequest.merchantId,
                    paymentKey = paymentRequest.paymentKey,
                    amount = paymentRequest.amount,
                    requestTime = paymentRequest.requestTime,
                    cardNumber = it.cardNumber,
                    expirationPeriod = it.expirationPeriod,
                    cvc = it.cvc
                )
            } ?: throw PaymentException.UserIdNotFoundException(
            paymentRequest.userId
        )
    }
}
