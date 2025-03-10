package com.inner.circle.api.controller.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForUserV1Api
import com.inner.circle.core.security.AccountDetails
import com.inner.circle.core.sse.SseConnectionPool
import com.inner.circle.core.usecase.PaymentTokenHandlingUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
        @AuthenticationPrincipal account: AccountDetails,
        @RequestParam merchantId: String,
        @RequestParam orderId: String
    ): ResponseBodyEmitter {
//        val checkPaymentStatus = paymentTokenHandlingUseCase.checkPaymentStatus(merchantId, orderId)
        val uniqueKey = "${merchantId}_$orderId"
        val connectionKey = account.id.toString()
        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                connectionKey,
                sseConnectionPool,
                objectMapper
            )

//        if (!checkPaymentStatus) {
//            val sseEmitter = SseEmitter()
//            sseEmitter.send("already end of process.")
//            sseEmitter.complete()
//            return sseEmitter
//        }

        log.error("SSE user ({}) connected.", uniqueKey)
        sseConnectionPool.addSession(uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }
}
