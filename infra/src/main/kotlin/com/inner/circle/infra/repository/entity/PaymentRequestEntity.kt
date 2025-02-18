package com.inner.circle.infra.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment_request")
data class PaymentRequestEntity(
    @Id
    @Tsid
    val id: Long?,
    @Column(name = "order_id", nullable = false)
    val orderId: String,
    @Column(name = "order_name")
    val orderName: String?,
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    val orderStatus: PaymentStatusType,
    @Column(name = "account_id")
    val accountId: Long?,
    @Column(name = "card_number")
    val cardNumber: String?,
    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType = PaymentType.CARD,
    @Column(name = "merchant_id", nullable = false)
    val merchantId: Long,
    @Column(name = "merchant_name")
    val merchantName: String,
    @Column(name = "payment_key")
    val paymentKey: String?,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(name = "payment_token")
    val paymentToken: String?,
    @Column(name = "request_time", nullable = false)
    val requestTime: LocalDateTime
) : BaseEntity()
