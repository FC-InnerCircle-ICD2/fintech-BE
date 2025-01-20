package com.inner.circle.core.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Payment(
    val paymentKey: String,
    val currency: String,
    val userId: String,
    val merchantId: String,
    val paymentType: String,
    val orderId: String
)
