package com.inner.circle.api.controller

import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
    ): PaymentResponse<PaymentClaimResponse> {
        val response = claimUseCase.createPayment(request, merchantId)
        return PaymentResponse.ok(response)
    }
}
