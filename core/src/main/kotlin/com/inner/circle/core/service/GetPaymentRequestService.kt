package com.inner.circle.core.service

import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.GetPaymentRequestUseCase
import com.inner.circle.infra.port.PaymentRequestPort
import org.springframework.stereotype.Service

@Service
internal class GetPaymentRequestService(
    private val paymentRequestPort: PaymentRequestPort
) : GetPaymentRequestUseCase {
    override fun getPaymentRequest(request: GetPaymentRequestUseCase.Request): PaymentRequestDto {
        return paymentRequestPort.findByPaymentKey(
            PaymentRequestPort.Request(
                request.paymentKey
            )
        ).let {
            PaymentRequestDto(
                orderId = it.orderId,
                orderName = it.orderName,
                cardNumber = it.cardNumber,
                orderStatus = it.orderStatus,
                userId = it.userId,
                merchantId = it.merchantId,
                paymentKey = it.paymentKey,
                amount = it.amount,
                requestTime = it.requestTime,
            )
        }
    }
}
