package com.inner.circle.api.util.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.format.DateTimeFormatter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

class KstDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    private val seoulTimeZone = "Asia/Seoul"

    override fun serialize(
        value: LocalDateTime,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        gen.writeString(
            value
                .toInstant(
                    TimeZone.UTC
                ).toLocalDateTime(
                    TimeZone.of(zoneId = seoulTimeZone)
                ).toJavaLocalDateTime()
                .format(formatter)
        )
    }
}
