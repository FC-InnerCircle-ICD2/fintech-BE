package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.adaptor.dto.ConfirmPaymentInfraDto
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
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
        val accountId =
            request.accountId
                ?: throw UserAuthenticationException.UserNotFoundException()
        val orderId = request.orderId
        val merchantId = request.merchantId
        val paymentRequest =
            paymentRequestRepository.findByOrderIdAndMerchantId(orderId, merchantId)
                ?: throw PaymentException.OrderNotFoundException(
                    orderId
                )

        val userCard =
            userCardRepository.findByAccountIdAndIsRepresentative(
                accountId,
                true
            ) ?: throw PaymentException.CardNotFoundException()

        return ConfirmPaymentInfraDto(
            orderId = paymentRequest.orderId,
            orderName = paymentRequest.orderName,
            orderStatus = PaymentProcessStatus.valueOf(paymentRequest.orderStatus.name),
            accountId = accountId,
            merchantId = paymentRequest.merchantId,
            merchantName = paymentRequest.merchantName,
            paymentKey = paymentRequest.paymentKey,
            amount = paymentRequest.amount,
            requestTime = paymentRequest.requestTime,
            cardNumber = userCard.cardNumber,
            expirationPeriod = userCard.expirationPeriod,
            cvc = userCard.cvc,
            cardCompany = userCard.cardCompany
        )
    }
}
