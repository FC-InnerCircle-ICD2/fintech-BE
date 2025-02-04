package com.inner.circle.api.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmSimplePaymentRequest
    @JsonCreator
    constructor(
        @JsonProperty("orderId") val orderId: String,
        @JsonProperty("merchantId") val merchantId: String
    )
