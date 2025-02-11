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
    override fun getPaymentByPaymentKey(request: GetPaymentUseCase.Request): PaymentDto =
        getPaymentPort
            .getPaymentByPaymentKey(
                GetPaymentPort.Request(
                    paymentKey = request.paymentKey
                )
            ).let {
                PaymentDto(
                    id = it.id,
                    paymentKey = it.paymentKey,
                    cardNumber = it.cardNumber,
                    currency = Currency.of(it.currency),
                    accountId = it.accountId,
                    merchantId = it.merchantId,
                    paymentType = PaymentType.of(it.paymentType),
                    orderId = it.orderId,
                    orderName = it.orderName,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
}
