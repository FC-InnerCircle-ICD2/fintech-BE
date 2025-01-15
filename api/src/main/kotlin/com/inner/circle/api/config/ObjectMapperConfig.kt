package com.inner.circle.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.inner.circle.api.util.serializer.KstDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {
    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper().apply {
            // jdk 8 버전 이후 클래스
            registerModule(Jdk8Module())
            // LocalDateTime, ZonedDateTime 처리
            registerModule(JavaTimeModule())

            // ObjectMapper에 KstDateTimeSerializer 추가
            val module =
                SimpleModule().apply {
                    addSerializer(LocalDateTime::class.java, KstDateTimeSerializer())
                }
            registerModule(module)

            // 모르는 JSON 필드는 무시
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // 날짜를 타임스탬프가 아닌 ISO 8601 형식으로 직렬화
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // 스네이크 케이스로 설정
            setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        }
}
