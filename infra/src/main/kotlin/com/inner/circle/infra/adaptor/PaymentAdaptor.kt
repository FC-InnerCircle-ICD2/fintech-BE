package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.infra.port.GetPaymentPort
import com.inner.circle.infra.port.PaymentPort
import com.inner.circle.infra.repository.PaymentRepository
import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : PaymentPort,
    GetPaymentPort {
    override fun save(request: PaymentPort.Request) {
        paymentRepository.save(
            PaymentEntity(
                id = request.id,
                paymentKey = request.paymentKey,
                cardNumber = request.cardNumber,
                currency = request.currency,
                accountId = request.accountId,
                merchantId = request.merchantId,
                paymentType = request.paymentType,
                orderId = request.orderId,
                orderName = request.orderName
            )
        ) ?: throw PaymentException.PaymentNotSaveException(
            paymentKey = request.paymentKey
        )
    }

    override fun findByAccountIdAndPaymentKey(request: GetPaymentPort.Request): PaymentEntity =
        paymentRepository.findByAccountIdAndPaymentKey(
            accountId = request.accountId,
            paymentKey = request.paymentKey
        )
            ?: throw PaymentException.PaymentNotFoundException(
                paymentId = "",
                message =
                    "Payment not found: " +
                        "accountId ${request.accountId} paymentKey ${request.paymentKey}"
            )
}
