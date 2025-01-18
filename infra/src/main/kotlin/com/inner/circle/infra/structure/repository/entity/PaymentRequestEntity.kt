package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment_request")
data class PaymentRequestEntity(
    @Id
    val orderId: String,
    @Column(name = "order_name")
    val orderName: String?,
    @Column(name = "order_status", nullable = false)
    val orderStatus: String,
    @Column(name = "user_id")
    val userId: Long?,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: String,
    @Column(name = "payment_key")
    val paymentKey: String?,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(name = "success_url", nullable = false)
    val successUrl: String,
    @Column(name = "fail_url", nullable = false)
    val failUrl: String,
    @Column(name = "request_time", nullable = false)
    val requestTime: LocalDateTime
)
