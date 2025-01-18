package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import com.inner.circle.infrabackoffice.port.FindPaymentByPaymentKeyPort
import com.inner.circle.infrabackoffice.repository.PaymentRepository
import java.util.Optional
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Component

@Component
internal class PaymentAdaptor(
    private val repository: PaymentRepository
) : FindPaymentByPaymentKeyPort {
    override fun findByPaymentKey(paymentKey: String): Optional<PaymentDto> =
        repository.findByPaymentKey(paymentKey).map {
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
        }
}
