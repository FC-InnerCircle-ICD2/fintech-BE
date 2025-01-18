package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.domain.Currency
import com.inner.circle.corebackoffice.domain.PaymentType
import com.inner.circle.corebackoffice.service.dto.PaymentDto
import com.inner.circle.corebackoffice.usecase.GetPaymentUseCase
import com.inner.circle.infrabackoffice.port.GetPaymentPort
import org.springframework.stereotype.Service

@Service
internal class PaymentService(
    private val getPaymentPort: GetPaymentPort
) : GetPaymentUseCase {
    override fun getPaymentByPaymentKey(request: GetPaymentUseCase.Request): PaymentDto {
        return getPaymentPort.getPaymentByPaymentKey(
            GetPaymentPort.Request(
                paymentKey = request.paymentKey
            )
        ).let {
            PaymentDto(
                id = it.id,
                paymentKey = it.paymentKey,
                currency = Currency.of(it.currency),
                userId = it.userId,
                merchantId = it.merchantId,
                paymentType = PaymentType.of(it.paymentType),
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }
}
