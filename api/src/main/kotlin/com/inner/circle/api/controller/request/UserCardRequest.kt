package com.inner.circle.api.controller.request

data class UserCardRequest(
    val userId: Long?,
    val representativeYn: Boolean?,
    val cardNumber: String?,
    val expirationPeriod: String?,
    val cvc: String?
)
