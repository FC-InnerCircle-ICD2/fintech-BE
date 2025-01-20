package com.inner.circle.api.payment.controller

import com.inner.circle.api.payment.interceptor.RequireAuth
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Claims", description = "Claim (결제 요청) API")
@RestController
@RequestMapping("/api/payments/v1")
class ClaimController(
    private val claimUseCase: PaymentClaimUseCase
) {
    @Operation(summary = "결제 요청")
    @RequireAuth
    @PostMapping
    fun createPayment(
        @RequestBody request: PaymentClaimUseCase.PaymentClaimRequest,
        @RequestParam(value = "merchantId") merchantId: String
    ): ResponseEntity<PaymentResponse<PaymentClaimResponse>> {
        val response = claimUseCase.createPayment(request, merchantId)
        val result = PaymentResponse.ok(response)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @RequireAuth
    @PostMapping("/{order_id}/proceed")
    fun proceedPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//           val response = paymentService.proceedPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment proceeded.")
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @RequireAuth
    @PostMapping("/{order_id}/cancel")
    fun cancelPayment(
        @PathVariable("order_id") orderId: String
    ): ResponseEntity<PaymentResponse<String>> {
//            val response = paymentService.cancelPayment(orderId, authorization)
        val response = PaymentResponse.ok("payment cancelled.")
        return ResponseEntity(response, HttpStatus.OK)
    }
}
