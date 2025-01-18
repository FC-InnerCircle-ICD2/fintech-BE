package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.adaptor.dto.CardPaymentAuthInfraDto

fun interface CardPaymentAuthPort {
    data class Request(
        val cardNumber: String
    )

    fun doPaymentAuth(request: Request): CardPaymentAuthInfraDto
}
