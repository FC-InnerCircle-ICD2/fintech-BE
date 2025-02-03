package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.port.SavePaymentRequestPort
import com.inner.circle.infra.repository.PaymentRequestRepository
import com.inner.circle.infra.repository.entity.PaymentRequestEntity
import org.springframework.stereotype.Component

@Component
internal class SavePaymentRequestAdaptor(
    private val paymentRequestRepository: PaymentRequestRepository
) : SavePaymentRequestPort {
    override fun save(request: SavePaymentRequestPort.Request) {
        val existingEntity =
            paymentRequestRepository.findByOrderIdAndMerchantId(request.orderId, request.merchantId)
                ?: throw PaymentException.PaymentRequestNotFoundException(
                    "",
                    "Payment request not found"
                )

        paymentRequestRepository.save(
            PaymentRequestEntity(
                id = existingEntity.id,
                orderId = request.orderId,
                orderName = request.orderName,
                orderStatus = request.orderStatus ?: PaymentProcessStatus.DONE.name,
                userId = request.userId,
                merchantId = request.merchantId,
                paymentKey = request.paymentKey,
                amount = request.amount,
                cardNumber = request.cardNumber,
                paymentType = request.paymentType,
                requestTime = request.requestTime,
                paymentToken = existingEntity.paymentToken
            )
        )
    }
}
