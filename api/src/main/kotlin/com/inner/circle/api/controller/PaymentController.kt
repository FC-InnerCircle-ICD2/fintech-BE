package com.inner.circle.api.controller

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.controller.dto.ConfirmPaymentDto
import com.inner.circle.api.controller.dto.PaymentApproveDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.ConfirmPaymentRequest
import com.inner.circle.api.controller.request.ConfirmSimplePaymentRequest
import com.inner.circle.api.controller.request.PaymentApproveRequest
import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import com.inner.circle.core.usecase.PaymentClaimUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payments", description = "결제 관련 API")
@PaymentV1Api
class PaymentController(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val claimUseCase: PaymentClaimUseCase,
    private val savePaymentApproveService: SavePaymentApproveUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    @RequireAuth
    @Operation(summary = "결제 요청")
    @PostMapping("/payments")
    fun createPayment(
        @RequestBody request: PaymentClaimUseCase.PaymentClaimRequest
    ): PaymentResponse<PaymentClaimUseCase.PaymentClaimResponse> {
        val merchantId = "tempMerchantId" // @AuthenticationPrincipal
        val response = claimUseCase.createPayment(request, merchantId)
        val status = PaymentStatusEventType.READY
        statusChangedMessageSender.sendProcessChangedMessage(
            PaymentStatusChangedSsePaymentRequest(
                eventType = status.getEventType(),
                status = status.name,
                orderId = request.orderId,
                merchantId = merchantId
            )
        )
        return PaymentResponse.ok(response)
    }

    @RequireAuth
    @Operation(summary = "결제 승인")
    @PostMapping("/payments/confirm")
    fun confirmPayment(
        @RequestBody paymentApproveRequest: PaymentApproveRequest
    ): PaymentResponse<PaymentApproveDto> =
        PaymentResponse.ok(
            savePaymentApproveService
                .saveApprove(
                    SavePaymentApproveUseCase.Request(
                        paymentKey = paymentApproveRequest.paymentKey,
                        orderId = paymentApproveRequest.orderId,
                        amount = paymentApproveRequest.amount
                    )
                ).let {
                    PaymentApproveDto(
                        orderId = it.orderId,
                        paymentKey = it.paymentKey,
                        amount = it.amount
                    )
                }
        )

    @RequireAuth
    @Operation(summary = "간편 결제 인증")
    @PostMapping("/payments/authentication/simple")
    fun proceedPaymentConfirm(
        @RequestBody confirmSimplePaymentRequest: ConfirmSimplePaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
        val response =
            PaymentResponse.ok(
                ConfirmPaymentDto.of(
                    confirmPaymentUseCase.confirmPayment(
                        ConfirmSimplePaymentUseCase.Request(
                            orderId = confirmSimplePaymentRequest.orderId,
                            merchantId = confirmSimplePaymentRequest.merchantId
                        )
                    )
                )
            )
        return response
    }

    @Operation(summary = "일반 결제 인증")
    @PostMapping("/payments/authentication")
    fun proceedPaymentConfirm(
        @RequestBody confirmPaymentRequest: ConfirmPaymentRequest
    ): PaymentResponse<ConfirmPaymentDto> {
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
        return response
    }

    @RequireAuth
    @Operation(summary = "결제 취소")
    @GetMapping("/payments/orders/{order_id}/cancel")
    fun cancelPaymentConfirm(
        @PathVariable("order_id") orderId: String
    ): PaymentResponse<String> {
        val response = PaymentResponse.ok("결제가 취소되었습니다.")
        return response
    }
}
