package com.inner.circle.api.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PaymentClaimRequest
    @JsonCreator
    constructor(
        @JsonProperty("amount") val amount: BigDecimal,
        @JsonProperty("orderId") val orderId: String,
        @JsonProperty("orderName") val orderName: String
    )
