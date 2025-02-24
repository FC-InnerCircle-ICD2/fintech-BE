package com.inner.circle.api.controller.request

data class UserCardRequest(
    val isRepresentative: Boolean,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String,
    val cardCompany: String
)
