package com.inner.circle.infrabackoffice.adaptor

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
                    it.id!!,
                    it.paymentKey,
                    it.currency,
                    it.userId,
                    it.merchantId,
                    it.paymentType,
                    it.createdAt.toKotlinLocalDateTime(),
                    it.updatedAt.toKotlinLocalDateTime()
                )
            } ?: throw IllegalArgumentException(
            "Payment not found - payment_key: ${request.paymentKey}"
        )
}
