package com.inner.circle.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.core.sse.SseConnectionPool
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter

@PaymentV1Api
class SseApiController(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    private val logger: Logger = LoggerFactory.getLogger(SseApiController::class.java)

    companion object {
        private val log = LoggerFactory.getLogger(SseApiController::class.java)
    }

    @GetMapping(path = ["/sse/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ): ResponseBodyEmitter {
        log.info("SSE user {}", merchantId + "_" + orderId)

        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                merchantId + "_" + orderId,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }

    @GetMapping("/sse/push-event")
    fun pushEvent(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        val sseConnection =
            sseConnectionPool.getSession(
                merchantId + "_" + orderId
            )

        sseConnection.sendMessage("hello world")
    }

    @GetMapping("/sse/push-event/ready")
    fun paymentReady(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.READY,
            orderId = orderId,
            merchantId = merchantId
        )
    }

    @GetMapping("/sse/push-event/verificate")
    fun paymentVerificate(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = orderId,
            merchantId = merchantId
        )
    }

    @GetMapping("/sse/push-event/progress")
    fun paymentProgress(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_PROGRESS,
            orderId = orderId,
            merchantId = merchantId
        )
    }

    @GetMapping("/sse/push-event/done")
    fun paymentDone(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.DONE,
            orderId = orderId,
            merchantId = merchantId
        )
    }

    @GetMapping("/sse/push-event/canceled")
    fun paymentCanceled(
        @Parameter(hidden = false)
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ) {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.CANCELED,
            orderId = orderId,
            merchantId = merchantId
        )
    }

    private fun sendStatusChangedMessage(
        status: PaymentStatusEventType,
        orderId: String,
        merchantId: String
    ) {
        try {
            statusChangedMessageSender.sendProcessChangedMessage(
                PaymentStatusChangedSsePaymentRequest(
                    eventType = status.getEventType(),
                    status = status.name,
                    orderId = orderId,
                    merchantId = merchantId
                )
            )
        } catch (e: Exception) {
            logger.error("Error while send ${status.name} Status.", e)
        }
    }
}
