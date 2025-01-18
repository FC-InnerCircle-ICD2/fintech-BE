package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto
import java.util.Optional

fun interface FindPaymentByPaymentKeyPort {
    fun findByPaymentKey(paymentKey: String): Optional<PaymentDto>
}
