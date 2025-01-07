package com.inner.circle.core.structure.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Payment(
    val userName: String,
    val amount: Long,
    val requestAt: LocalDateTime =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
)
