package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.ConfirmPaymentInfraDto

fun interface ConfirmPaymentPort {
    data class Request(
        val orderId: String,
        val merchantId: String
    )

    fun getCardNoAndPayInfo(request: Request): ConfirmPaymentInfraDto
}
