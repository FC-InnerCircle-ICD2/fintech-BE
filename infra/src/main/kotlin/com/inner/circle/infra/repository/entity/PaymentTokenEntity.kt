package com.inner.circle.infra.repository.entity

import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

data class PaymentTokenEntity(
    val merchantId: Long,
    val orderId: String,
    val generatedToken: String
) {
    companion object {
        fun fromToken(tokenString: String): PaymentTokenEntity {
            val parts = tokenString.split(",")
            return PaymentTokenEntity(
                merchantId = parts[0].toLong(),
                orderId = parts[1],
                generatedToken = parts[2]
            )
        }
    }

    override fun toString(): String = "$merchantId,$orderId,$generatedToken}"
}
