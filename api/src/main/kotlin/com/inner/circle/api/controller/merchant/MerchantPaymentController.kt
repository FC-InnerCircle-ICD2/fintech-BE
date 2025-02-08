package com.inner.circle.api.controller.merchant

import com.inner.circle.api.application.PaymentStatusChangedMessageSender
import com.inner.circle.api.application.dto.PaymentStatusChangedSsePaymentRequest
import com.inner.circle.api.application.dto.PaymentStatusEventType
import com.inner.circle.api.controller.PaymentV1Api
import com.inner.circle.api.controller.dto.PaymentApproveDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.PaymentApproveRequest
import com.inner.circle.api.controller.request.PaymentClaimRequest
import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.core.service.dto.MerchantDto
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.PaymentClaimUseCase
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

@Tag(name = "Payments - Merchant", description = "상점 고객(SDK) 결제 관련 API")
@PaymentV1Api
class MerchantPaymentController(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    private val claimUseCase: PaymentClaimUseCase,
    private val savePaymentApproveService: SavePaymentApproveUseCase,
    private val statusChangedMessageSender: PaymentStatusChangedMessageSender
) {
    private val logger: Logger = LoggerFactory.getLogger(MerchantPaymentController::class.java)

    @RequireAuth
    @Operation(summary = "결제 요청")
    @PostMapping("/merchant/payments")
    fun createPayment(
        @RequestBody request: PaymentClaimRequest,
        servletRequest: HttpServletRequest
    ): PaymentResponse<PaymentClaimUseCase.PaymentClaimResponse> {
        val merchantDto = servletRequest.getAttribute("merchantUser") as MerchantDto
        val merchantId = merchantDto.merchantId

        val claimRequest =
            PaymentClaimUseCase.ClaimRequest(
                amount = request.amount,
                orderId = request.orderId,
                orderName = request.orderId
            )

        val response = claimUseCase.createPayment(claimRequest, merchantId)

        sendStatusChangedMessage(
            status = PaymentStatusEventType.READY,
            orderId = request.orderId,
            merchantId = merchantId
        )

        return PaymentResponse.ok(response)
    }

    @RequireAuth
    @Operation(summary = "결제 승인")
    @PostMapping("/merchant/payments/confirm")
    fun confirmPayment(
        @RequestBody paymentApproveRequest: PaymentApproveRequest,
        servletRequest: HttpServletRequest
    ): PaymentResponse<PaymentApproveDto> {
        val merchantDto = servletRequest.getAttribute("merchantUser") as MerchantDto
        val merchantId = merchantDto.merchantId

        val data =
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

        sendStatusChangedMessage(
            status = PaymentStatusEventType.DONE,
            orderId = paymentApproveRequest.orderId,
            merchantId = merchantId
        )

        return PaymentResponse.ok(
            data
        )
    }

    @RequireAuth
    @Operation(summary = "결제 취소")
    @GetMapping("/merchant/payments/orders/{payment_key}/cancel")
    fun cancelPaymentConfirm(
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
