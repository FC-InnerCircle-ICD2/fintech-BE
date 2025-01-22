package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentRequestEntity(
    @Id
    val orderId: String,
    @Column(name = "order_name")
    val orderName: String?,
    @Column(name = "order_status")
    val orderStatus: String?,
    @Column(name = "user_id")
    val userId: Long?,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: String,
    @Column(name = "payment_key")
    val paymentKey: String?,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(name = "request_time")
    val requestTime: LocalDateTime
)
