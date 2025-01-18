package com.inner.circle.infra.structure.adaptor.dto

import java.math.BigDecimal

data class MobilePaymentInfraDto(
    val orderId: String,
    val cardNumber: String,
    val amount: BigDecimal
)
