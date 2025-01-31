package com.inner.circle.infra.repository.entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

data class PaymentTokenEntity(
    val merchantId: String,
    val orderId: String,
    val generatedToken: String,
    val expiresAt: LocalDateTime
) {
    companion object {
        fun fromToken(tokenString: String): PaymentTokenEntity {
            val parts = tokenString.split(",")
            return PaymentTokenEntity(
                merchantId = parts[0],
                orderId = parts[1],
                generatedToken = parts[2],
                expiresAt = LocalDateTime.parse(parts[3], dateTimeFormatter)
            )
        }
    }

    override fun toString(): String =
        "$merchantId,$orderId,$generatedToken,${expiresAt.format(dateTimeFormatter)}"
}
