package com.inner.circle.api.controller

import com.inner.circle.api.controller.request.PaymentApproveRequest
import com.inner.circle.api.controller.response.PaymentApproveResponse
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.domain.Currency
import com.inner.circle.core.service.dto.PaymentRequestDto
import com.inner.circle.core.usecase.GetPaymentRequestUseCase
import com.inner.circle.core.usecase.HttpUseCase
import com.inner.circle.core.usecase.SavePaymentApproveUseCase
import com.inner.circle.core.usecase.SavePaymentUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payment/v1")
class ConfirmController  (
    private val savePaymentApproveService:SavePaymentApproveUseCase,
) {
    @Operation(summary = "결제 승인 API")
    @PostMapping("/confirm")
    fun confirmPayment(
        @RequestBody paymentApproveRequest: PaymentApproveRequest
    ): ResponseEntity<PaymentResponse<PaymentApproveResponse>> {

        savePaymentApproveService.saveApprove(
            SavePaymentApproveUseCase.Request(
                paymentKey = paymentApproveRequest.paymentKey,
                orderId = paymentApproveRequest.order_id,
            )
        )
        
        return ResponseEntity.ok(
            PaymentResponse.ok(
                PaymentApproveResponse(
                    order_id = paymentApproveRequest.order_id,
                    paymentKey= paymentApproveRequest.paymentKey,
                    amount= paymentApproveRequest.amount.toBigDecimal()
                )
            )
        )
    }
}
