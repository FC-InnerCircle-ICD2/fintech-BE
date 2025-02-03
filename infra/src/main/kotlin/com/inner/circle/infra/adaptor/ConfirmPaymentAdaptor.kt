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

        val userCard = paymentRequest.accountId?.let { userCardRepository.findByAccountId(it) }

        return ConfirmPaymentInfraDto(
            orderId = paymentRequest.orderId,
            orderName = paymentRequest.orderName,
            orderStatus = paymentRequest.orderStatus,
            accountId = paymentRequest.accountId,
            merchantId = paymentRequest.merchantId,
            paymentKey = paymentRequest.paymentKey,
            amount = paymentRequest.amount,
            requestTime = paymentRequest.requestTime,
            cardNumber = userCard?.cardNumber,
            expirationPeriod = userCard?.expirationPeriod,
            cvc = userCard?.cvc
        )
    }
}
