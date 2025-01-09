package com.inner.circle.infra.structure.repository.entity

import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class PaymentEntity(
    var userName: String,
    val amount: Long,
    val requestAt: LocalDateTime
) {
    var id: UUID? = null
        private set
    val createdAt: LocalDateTime =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())

    companion object {
        fun createWithId(
            userName: String,
            amount: Long,
            requestAt: LocalDateTime,
            id: UUID
        ): PaymentEntity =
            PaymentEntity(userName, amount, requestAt).apply {
                this.id = id
            }
    }

    fun update(entity: PaymentEntity): PaymentEntity {
        this.id = entity.id
        this.userName = entity.userName
        return this
    }
}
