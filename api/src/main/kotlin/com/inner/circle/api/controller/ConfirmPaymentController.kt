package com.inner.circle.api.controller

import com.inner.circle.api.controller.dto.ConfirmPaymentDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.ConfirmPaymentRequest
import com.inner.circle.api.controller.request.ConfirmSimplePaymentRequest
import com.inner.circle.core.usecase.ConfirmPaymentUseCase
import com.inner.circle.core.usecase.ConfirmSimplePaymentUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "ConfirmPayment", description = "ConfirmPayment API")
@PaymentV1Api
class ConfirmPaymentController(
    private val confirmPaymentUseCase: ConfirmPaymentUseCase
) {
    @Operation(summary = "간편 결제 확인")
    @PostMapping("/proceed/simple")
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

    @Operation(summary = "결제 확인")
    @PostMapping("/proceed")
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

    // Frontend 분들과 협의 필요
    @Operation(summary = "결제 취소")
    @GetMapping("/cancel/{order_id}")
    fun cancelPaymentConfirm(
        @PathVariable("order_id") orderId: String
    ): PaymentResponse<String> {
        val response = PaymentResponse.ok("결제가 취소되었습니다.")
        return response
    }
}
