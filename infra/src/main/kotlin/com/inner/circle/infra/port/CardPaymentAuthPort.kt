package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.CardPaymentAuthInfraDto

fun interface CardPaymentAuthPort {
    data class Request(
        val cardNumber: String
    )

    fun doPaymentAuth(request: Request): CardPaymentAuthInfraDto
}
