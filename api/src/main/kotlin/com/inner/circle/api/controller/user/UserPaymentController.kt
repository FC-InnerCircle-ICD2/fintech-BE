package com.inner.circle.api.controller.user

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.controller.PaymentForUserV1Api
import com.inner.circle.api.controller.dto.ConfirmPaymentDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.ConfirmPaymentRequest
import com.inner.circle.api.controller.request.ConfirmSimplePaymentRequest
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payments - User", description = "결제 고객(App) 결제 관련 API")
@PaymentForUserV1Api
class UserPaymentController(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val savePaymentApproveService: SavePaymentApproveUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    private val logger: Logger = LoggerFactory.getLogger(UserPaymentController::class.java)

    @Operation(summary = "간편 결제 인증")
    @PostMapping("/authentication/simple")
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
    @PostMapping("/authentication")
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

    @Operation(summary = "결제 취소 - paymentKey")
    @PostMapping("/orders/{paymentKey}/cancel")
    fun cancelPaymentConfirmWithOrderId(
        @PathVariable("payment_key") paymentKey: String,
        servletRequest: HttpServletRequest
    ): PaymentResponse<String> {
        val response = PaymentResponse.ok("결제가 취소되었습니다.")

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
