package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.MobilePaymentInfraDto

fun interface MobilePaymentPort {
    data class Request(
        val orderId: String
    )

    fun getCardNoAndPayInfo(request: Request): MobilePaymentInfraDto
}
