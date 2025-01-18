package com.inner.circle.api.payment.controller.dto

data class UserCardDto(
    val userId: Long?,
    val representativeYn: Boolean?,
    val cardNumber: String?,
    val expirationPeriod: String?,
    val cvc: String?
)
