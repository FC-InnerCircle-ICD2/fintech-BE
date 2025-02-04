package com.inner.circle.api.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmPaymentRequest
    @JsonCreator
    constructor(
        @JsonProperty("orderId") val orderId: String,
        @JsonProperty("merchantId") val merchantId: String,
        @JsonProperty("cardNumber") val cardNumber: String,
        @JsonProperty("expirationPeriod") val expirationPeriod: String,
        @JsonProperty("cvc") val cvc: String
    )
