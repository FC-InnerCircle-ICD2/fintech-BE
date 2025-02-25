package com.inner.circle.core.service

import com.inner.circle.core.domain.PaymentProcessStatus
import com.inner.circle.core.service.dto.CancelPaymentDto
import com.inner.circle.core.usecase.CancelPaymentRequestUseCase
import com.inner.circle.infra.port.ConfirmPaymentPort
import com.inner.circle.infra.port.SavePaymentRequestPort
import com.inner.circle.infra.repository.entity.PaymentType
import org.springframework.stereotype.Service
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus as InfraPaymentProcessStatus

@Service
internal class CancelPaymentRequestService(
    private val confirmPaymentPort: ConfirmPaymentPort,
    private val savePaymentRequestPort: SavePaymentRequestPort
) : CancelPaymentRequestUseCase {
    override fun cancel(request: CancelPaymentRequestUseCase.Request): CancelPaymentDto {
        val paymentInfo =
            confirmPaymentPort.getCardNoAndPayInfo(
                ConfirmPaymentPort.Request(
                    orderId = request.orderId,
                    merchantId = request.merchantId,
                    accountId = request.accountId
                )
            )

        savePaymentRequestPort.save(
            SavePaymentRequestPort.Request(
                orderId = paymentInfo.orderId,
                orderName = paymentInfo.orderName,
                orderStatus = InfraPaymentProcessStatus.CANCELED,
                accountId = paymentInfo.accountId,
                merchantId = paymentInfo.merchantId,
                merchantName = paymentInfo.merchantName,
                paymentKey = paymentInfo.paymentKey,
                amount = paymentInfo.amount,
                cardNumber = paymentInfo.cardNumber,
                paymentType = PaymentType.CARD,
                requestTime = paymentInfo.requestTime
            )
        )

        return CancelPaymentDto(
            orderId = request.orderId,
            merchantId = request.merchantId,
            accountId = request.accountId,
            orderStatus = PaymentProcessStatus.CANCELED
        )
    }
}
