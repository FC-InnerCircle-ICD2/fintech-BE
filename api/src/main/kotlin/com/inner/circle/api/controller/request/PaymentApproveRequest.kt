package com.inner.circle.api.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PaymentApproveRequest
    @JsonCreator
    constructor(
        @JsonProperty("orderId") val orderId: String,
        @JsonProperty("paymentKey") val paymentKey: String,
        @JsonProperty("amount") val amount: BigDecimal
    )
