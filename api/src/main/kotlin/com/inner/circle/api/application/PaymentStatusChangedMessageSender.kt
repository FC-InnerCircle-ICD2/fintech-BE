package com.inner.circle.api.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.application.dto.PaymentStatusChangedResponse
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.core.sse.SseConnectionPool
import com.inner.circle.exception.SseException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentStatusChangedMessageSender(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper
) {
    companion object {
        private val log = LoggerFactory.getLogger(PaymentStatusChangedMessageSender::class.java)
    }

    fun sendProcessChangedMessage(ssePaymentRequest: PaymentStatusChangedSsePaymentRequest) {
        val merchantId = ssePaymentRequest.merchantId
        val orderId = ssePaymentRequest.orderId
        val eventType = ssePaymentRequest.eventType

        try {
            val uniqueKey = merchantId + "_" + orderId
            val session =
                sseConnectionPool.getSession(
                    uniqueKey
                )
            val eventData = PaymentStatusChangedResponse.of(eventType, orderId, uniqueKey)
            val eventMessage = objectMapper.writeValueAsString(eventData)

            session.sendMessage(eventType, eventMessage)
            log.info(
                "sse message send. (merchantId: {}, orderId: {}, eventType: {})",
                merchantId,
                orderId,
                eventType
            )
        } catch (e: NoSuchElementException) {
            log.error("get sse session failed", e)
            throw SseException.connectionNotFound(merchantId, orderId)
        }
    }
}
