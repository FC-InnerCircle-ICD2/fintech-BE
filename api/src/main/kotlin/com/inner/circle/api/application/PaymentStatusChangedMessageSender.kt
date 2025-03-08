package com.inner.circle.api.application

import com.inner.circle.api.application.dto.PaymentStatusChangedResponse
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.core.service.dto.ConfirmPaymentCoreDto
import com.inner.circle.core.usecase.SseConnectionUseCase
import com.inner.circle.exception.SseException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PaymentStatusChangedMessageSender(
    private val sseConnectionUseCase: SseConnectionUseCase
) {
    companion object {
        private val log = LoggerFactory.getLogger(PaymentStatusChangedMessageSender::class.java)
    }

    fun sendProcessChangedMessage(ssePaymentRequest: PaymentStatusChangedSsePaymentRequest) {
        val merchantId = ssePaymentRequest.merchantId
        val orderId = ssePaymentRequest.orderId
        val eventType = ssePaymentRequest.eventType

        try {
            val uniqueKey = "${merchantId}_$orderId"
            sseConnectionUseCase.sendMessage(
                SseConnectionUseCase.SendMessageRequest(
                    uniqueKey = uniqueKey,
                    eventName = eventType,
                    message = PaymentStatusChangedResponse.of(eventType, orderId, merchantId)
                )
            )

            log.error(
                "sse message send. (merchantId: {}, orderId: {}, eventType: {})",
                merchantId,
                orderId,
                eventType
            )
        } catch (e: NoSuchElementException) {
            log.error("get sse session failed", e)
            throw SseException.ConnectionNotFoundException(merchantId.toString(), orderId)
        }
    }

    fun sendPaymentAuthResultMessage(
        statusEventType: PaymentStatusEventType,
        authResult: ConfirmPaymentCoreDto
    ) {
        val merchantId = authResult.merchantId
        val orderId = authResult.orderId
        val eventType = statusEventType.getEventType()

        try {
            val uniqueKey = "${merchantId}_$orderId"
            sseConnectionUseCase.sendMessage(
                SseConnectionUseCase.SendMessageRequest(
                    uniqueKey = uniqueKey,
                    eventName = eventType,
                    message = authResult
                )
            )

            log.error(
                "sse message send. (merchantId: {}, orderId: {}, eventType: {})",
                merchantId,
                orderId,
                eventType
            )
        } catch (e: NoSuchElementException) {
            log.error("get sse session failed", e)
            throw SseException.ConnectionNotFoundException(merchantId.toString(), orderId)
        }
    }

    fun removeSessions(
        merchantId: Long,
        orderId: String
    ) {
        val uniqueKey = "${merchantId}_$orderId"
        sseConnectionUseCase.close(uniqueKey = uniqueKey)
        log.error("remove sse session. (uniqueKey: $uniqueKey)")
    }
}
