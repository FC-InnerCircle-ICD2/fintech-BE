package com.inner.circle.infrabackoffice.repository.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "payment_transaction")
data class TransactionEntity(
    @Id
    @Tsid
    val id: Long?,
    @Column(name = "payment_key", nullable = false, unique = true)
    val paymentKey: String,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: TransactionStatus,
    val reason: String?,
//    TODO: paymeny쪽과 협의 후 정리 필요 잠시 주석 처리
//    @Column(name = "request_time", nullable = false)
//    var requestTime: LocalDateTime?,
//    @Column(name = "completion_time")
//    var completionTime: LocalDateTime?
) : BaseEntity()
