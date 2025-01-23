package com.inner.circle.api.controller

import com.inner.circle.api.controller.dto.PaymentApproveDto
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.request.PaymentApproveRequest
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "PaymentApprove", description = "PaymentApprove API")
@PaymentV1Api
class ConfirmController(
    private val savePaymentApproveService: SavePaymentApproveUseCase
) {
    @Operation(summary = "결제 승인 API")
    @PostMapping("/confirm")
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
}
