package com.inner.circle.core.service.dto

data class UserCardDto(
    val id: Long?,
    val accountId: Long,
    val isRepresentative: Boolean,
    val cardNumber: String,
    val expirationPeriod: String,
    val cvc: String,
    val cardCompany: String
)
