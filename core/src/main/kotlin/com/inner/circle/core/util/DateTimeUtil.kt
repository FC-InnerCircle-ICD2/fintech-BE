package com.inner.circle.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    fun LocalDateTime.Companion.currentDateTime(): LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.UTC)

    fun LocalDate.Companion.currentDate(): LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.UTC).date
}
