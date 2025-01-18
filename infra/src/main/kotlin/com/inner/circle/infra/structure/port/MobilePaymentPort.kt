package com.inner.circle.infra.structure.port

import com.inner.circle.infra.structure.adaptor.dto.MobilePaymentInfraDto

fun interface MobilePaymentPort {
    data class Request(
        val orderId: String
    )

    fun getCardNoAndPayInfo(request: Request): MobilePaymentInfraDto
}
