package com.inner.circle.api.custom

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

class TimeBasedDuplicateMessageFilter : Filter<ILoggingEvent>() {
    companion object {
        private const val TIME_INTERVAL = 60000L // 60초
    }

    private val messageTimestamps = mutableMapOf<String, Long>()

    override fun decide(event: ILoggingEvent): FilterReply {
        val message = event.formattedMessage
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
