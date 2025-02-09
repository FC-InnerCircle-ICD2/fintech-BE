package com.inner.circle.api.controller.user

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.controller.PaymentV1Api
import com.inner.circle.api.controller.dto.ConfirmPaymentDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.ConfirmPaymentRequest
import com.inner.circle.api.controller.request.ConfirmSimplePaymentRequest
import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.core.service.dto.MerchantDto
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payments - User", description = "결제 고객(App) 결제 관련 API")
@PaymentV1Api
class UserPaymentController(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val savePaymentApproveService: SavePaymentApproveUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    private val logger: Logger = LoggerFactory.getLogger(UserPaymentController::class.java)

    @RequireAuth
    @Operation(summary = "간편 결제 인증")
    @PostMapping("/user/payments/authentication/simple")
    fun proceedPaymentConfirm(
        @RequestBody confirmSimplePaymentRequest: ConfirmSimplePaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = confirmSimplePaymentRequest.orderId,
            merchantId = confirmSimplePaymentRequest.merchantId
        )

        val data =
            ConfirmPaymentDto.of(
                confirmPaymentUseCase.confirmPayment(
                    ConfirmSimplePaymentUseCase.Request(
                        orderId = confirmSimplePaymentRequest.orderId,
                        merchantId = confirmSimplePaymentRequest.merchantId
                    )
                )
            )
        val response =
            PaymentResponse.ok(
                data
            )

        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_PROGRESS,
            orderId = confirmSimplePaymentRequest.orderId,
            merchantId = confirmSimplePaymentRequest.merchantId
        )

        return response
    }

    @Operation(summary = "일반 결제 인증")
    @PostMapping("/user/payments/authentication")
    fun proceedPaymentConfirm(
        @RequestBody confirmPaymentRequest: ConfirmPaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_VERIFICATE,
            orderId = confirmPaymentRequest.orderId,
            merchantId = confirmPaymentRequest.merchantId
        )

        val response =
            PaymentResponse.ok(
                ConfirmPaymentDto.of(
                    confirmPaymentUseCase.confirmPayment(
                        ConfirmPaymentUseCase.Request(
                            orderId = confirmPaymentRequest.orderId,
                            merchantId = confirmPaymentRequest.merchantId,
                            cardNumber = confirmPaymentRequest.cardNumber,
                            expirationPeriod = confirmPaymentRequest.expirationPeriod,
                            cvc = confirmPaymentRequest.cvc
                        )
                    )
                )
            )

        sendStatusChangedMessage(
            status = PaymentStatusEventType.IN_PROGRESS,
            orderId = confirmPaymentRequest.orderId,
            merchantId = confirmPaymentRequest.merchantId
        )
        return response
    }

    @RequireAuth
    @Operation(summary = "결제 취소")
    @GetMapping("/user/payments/orders/{order_id}/cancel")
    fun cancelPaymentConfirm(
        @PathVariable("order_id") orderId: String,
        servletRequest: HttpServletRequest
    ): PaymentResponse<String> {
        val merchantDto = servletRequest.getAttribute("merchantUser") as MerchantDto
        val merchantId = merchantDto.merchantId
        val response = PaymentResponse.ok("결제가 취소되었습니다.")

        val status = PaymentStatusEventType.CANCELED
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

        sendStatusChangedMessage(
            status = PaymentStatusEventType.CANCELED,
            orderId = orderId,
            merchantId = merchantId
        )

        return response
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
