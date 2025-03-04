package com.inner.circle.api.custom

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.turbo.TurboFilter
import ch.qos.logback.core.spi.FilterReply
import org.slf4j.Marker

class TimeBasedDuplicateMessageFilter : TurboFilter() {
    companion object {
        private const val TIME_INTERVAL = 60000L // 60초
    }

    private val messageTimestamps = mutableMapOf<String, Long>()


    override fun decide(
        marker: Marker?,
        logger: Logger?,
        level: Level?,
        format: String?,
        params: Array<out Any>?,
        t: Throwable?
    ): FilterReply {
        val message = format ?: return FilterReply.NEUTRAL
        val currentTime = System.currentTimeMillis()

        // 메시지가 이미 기록된 적이 있는 경우
        if (messageTimestamps.containsKey(message)) {
            val lastTime = messageTimestamps[message] ?: return FilterReply.NEUTRAL

            // 시간 간격 내에 중복된 메시지인 경우 필터링
            if (currentTime - lastTime < TIME_INTERVAL) {
                return FilterReply.DENY
            }
        }

        // 메시지 기록
        messageTimestamps[message] = currentTime
        return FilterReply.NEUTRAL
    }
}
