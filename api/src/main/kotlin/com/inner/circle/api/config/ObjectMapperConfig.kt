package com.inner.circle.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.module.SimpleModule
import com.inner.circle.api.util.serializer.KstDateTimeSerializer
import kotlinx.datetime.LocalDateTime

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {

            registerModule(Jdk8Module())  // jdk 8 버전 이후 클래스
            registerModule(JavaTimeModule())  // LocalDateTime, ZonedDateTime 처리


            val module = SimpleModule().apply {
                addSerializer(LocalDateTime::class.java, KstDateTimeSerializer())  // KstDateTimeSerializer 등록
            }
            registerModule(module)  // ObjectMapper에 KstDateTimeSerializer 추가

            // JSON 파싱 설정
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)  // 모르는 JSON 필드는 무시
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // 날짜를 타임스탬프가 아닌 ISO 8601 형식으로 직렬화
            setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)  // 스네이크 케이스로 설정
        }
    }
}
