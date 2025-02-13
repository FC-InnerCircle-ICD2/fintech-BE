package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.PaymentDto

fun interface  GetPaymentPort {
    data class Request(
        val paymentKey: String
    )

    fun getPaymentByPaymentKey(request: Request): PaymentDto
}
