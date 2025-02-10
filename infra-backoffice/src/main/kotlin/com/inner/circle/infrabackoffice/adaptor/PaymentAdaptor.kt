package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.exception.PaymentException.PaymentNotFoundException
import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import com.inner.circle.infrabackoffice.repository.PaymentRepository
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val paymentRepository: PaymentRepository
) : GetPaymentPort {
    override fun getPaymentByPaymentKey(request: GetPaymentPort.Request): PaymentDto =
        paymentRepository
            .findByPaymentKey(request.paymentKey)
            ?.let {
                PaymentDto(
                    id = requireNotNull(it.id),
                    paymentKey = it.paymentKey,
                    cardNumber = it.cardNumber,
                    currency = it.currency,
                    accountId = it.accountId,
                    merchantId = it.merchantId,
                    paymentType = it.paymentType,
                    orderId = it.orderId,
                    orderName = it.orderName,
                    createdAt = it.createdAt.toKotlinLocalDateTime(),
                    updatedAt = it.updatedAt.toKotlinLocalDateTime()
                )
            } ?: throw PaymentNotFoundException(
            paymentId = request.paymentKey,
            message = "Payment with PaymentKey ${request.paymentKey} not found"
        )
}
