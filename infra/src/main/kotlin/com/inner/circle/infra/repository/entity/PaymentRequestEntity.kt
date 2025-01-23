package com.inner.circle.infra.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment_request")
data class PaymentRequestEntity(
    @Id
    @Column(name = "payment_key")
    val paymentKey: String,
    @Column(name = "payment_type")
    val paymentType: String,
    @Column(name = "order_id")
    val orderId: String,
    @Column(name = "order_name")
    val orderName: String?,
    @Column(name = "card_number")
    val cardNumber: String,
    @Column(name = "order_status")
    val orderStatus: String?,
    @Column(name = "user_id")
    val userId: Long?,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: String,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(name = "request_time")
    var requestTime: LocalDateTime
) : BaseEntity() {
    @PrePersist
    fun prePersist() {
        if (requestTime == null) {
            requestTime = LocalDateTime.now()
        }
    }
}
