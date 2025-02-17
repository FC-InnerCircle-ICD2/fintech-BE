package com.inner.circle.api.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForUserV1Api
import com.inner.circle.core.sse.SseConnectionPool
import com.inner.circle.core.usecase.PaymentTokenHandlingUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter

@Tag(name = "SSE - user", description = "결제 고객(App) SSE API")
@PaymentForUserV1Api
@SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
class UserSseApiController(
    private val paymentTokenHandlingUseCase: PaymentTokenHandlingUseCase,
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(UserSseApiController::class.java)

    @GetMapping(path = ["/sse/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @RequestParam token: String
    ): ResponseBodyEmitter {
        val foundPaymentToken =
            paymentTokenHandlingUseCase.findPaymentToken(
                token
            )
        val orderId = foundPaymentToken.orderId
        val merchantId = foundPaymentToken.merchantId
        val uniqueKey = "${merchantId}_$orderId"
        log.info("SSE user ({}) connected.", uniqueKey)
        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                uniqueKey,
                sseConnectionPool,
                objectMapper
            )

        sseConnectionPool.addSession(sseConnection.uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }
}
