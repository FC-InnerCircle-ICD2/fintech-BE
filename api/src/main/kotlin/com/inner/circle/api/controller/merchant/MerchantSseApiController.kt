package com.inner.circle.api.controller.merchant

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForMerchantV1Api
import com.inner.circle.core.security.MerchantUserDetails
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

@Tag(name = "SSE - Merchant", description = "상점 고객(SDK) SSE API")
@PaymentForMerchantV1Api
@SecurityRequirement(name = SwaggerConfig.BASIC_AUTH)
class MerchantSseApiController(
    private val sseConnectionPool: SseConnectionPool,
    private val objectMapper: ObjectMapper,
    private val paymentTokenHandlingUseCase: PaymentTokenHandlingUseCase
) {
    private val log = LoggerFactory.getLogger(MerchantSseApiController::class.java)

    @GetMapping(path = ["/sse/connect"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @AuthenticationPrincipal merchantUserDetails: MerchantUserDetails,
        @RequestParam orderId: String
    ): ResponseBodyEmitter {
        val merchantId = merchantUserDetails.getId().toString()
        val uniqueKey = merchantId + "_" + orderId
        val connectionKey = merchantId
        val sseConnection =
            com.inner.circle.core.sse.SseConnection.connect(
                connectionKey,
                sseConnectionPool,
                objectMapper
            )

        log.error("SSE merchant ({}) connected.", uniqueKey)
        sseConnectionPool.addSession(uniqueKey, sseConnection)

        return sseConnection.sseEmitter
    }
}
